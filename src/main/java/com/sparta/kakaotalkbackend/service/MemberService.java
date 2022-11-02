package com.sparta.kakaotalkbackend.service;

import com.sparta.kakaotalkbackend.domain.ResponseDto;
import com.sparta.kakaotalkbackend.domain.jwt.RefreshToken;
import com.sparta.kakaotalkbackend.domain.jwt.TokenDto;
import com.sparta.kakaotalkbackend.domain.member.Member;
import com.sparta.kakaotalkbackend.domain.member.MemberRequestDto;
import com.sparta.kakaotalkbackend.domain.member.SigninRequestDto;
import com.sparta.kakaotalkbackend.jwt.JwtProvider;
import com.sparta.kakaotalkbackend.repository.MemberRepository;
import com.sparta.kakaotalkbackend.repository.RefreshTokenRepository;
import com.sparta.kakaotalkbackend.util.AmazonS3ResourceStorage;
import com.sparta.kakaotalkbackend.util.MultipartUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final RefreshTokenRepository refreshTokenRepository;
    private final AmazonS3ResourceStorage amazonS3ResourceStorage;
    private final JwtProvider jwtProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final RedisTemplate redisTemplate;



    //가입한 회원인지 아닌지 유효성 검사해주는 method
    public Member isPresentMember(String username) {
        Optional<Member> optionalMember = memberRepository.findByUsername(username);
        return optionalMember.orElse(null);
    }

    //회원가입
    @Transactional
    public ResponseDto<String> registerUser(MemberRequestDto memberRequestDto, MultipartFile multipartFile) {

        //중복처리
        if (null != isPresentMember(memberRequestDto.getUsername())) {
            return ResponseDto.fail(409, "중복 아이디입니다", "Conflict");
        }

        //비밀번호 확인
        if (!memberRequestDto.getPassword().equals(memberRequestDto.getPasswordCheck())) {
            return ResponseDto.fail(409, "비밀번호가 일치하지 않습니다", "Conflict");
        }

		/*
		이미지 업로드
		 */
        String image = MultipartUtil.createPath(multipartFile);
        amazonS3ResourceStorage.store(image, multipartFile);

        Member member = Member.builder()
                .username(memberRequestDto.getUsername())
                .nickname(memberRequestDto.getNickname())
                .image("https://kang--bucket.s3.ap-northeast-2.amazonaws.com/" + image)
                .status(memberRequestDto.getStatus())
                .password(passwordEncoder.encode(memberRequestDto.getPassword()))
                .build();
        memberRepository.save(member);
        return ResponseDto.success("회원가입 완료");
    }

    //로그인
    public ResponseDto<String> signin(SigninRequestDto signinRequestDto, HttpServletResponse httpServletResponse) {

        Member member = isPresentMember(signinRequestDto.getUsername());

        //사용자가 있는지 여부
        if (null == member) {
            return ResponseDto.fail(404, "사용자를 찾을 수 없습니다.", "Not Found");
        }

        //비밀번호가 맞는지 확인
        if (!member.validatePassword(passwordEncoder, signinRequestDto.getPassword())) {
            return ResponseDto.fail(409, "비밀번호가 일치하지 않습니다", "Conflict");
        }

        // 1. Login ID/PW 를 기반으로 AuthenticationToken 생성
        UsernamePasswordAuthenticationToken authenticationToken
                = new UsernamePasswordAuthenticationToken(signinRequestDto.getUsername(), signinRequestDto.getPassword());

        // 2. 실제로 검증 (사용자 비밀번호 체크) 이 이루어지는 부분
        //    authenticate 메서드가 실행이 될 때 CustomUserDetailsService 에서 만들었던 loadUserByUsername 메서드가 실행됨
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        TokenDto tokenDto = jwtProvider.generateToken(authentication);

        RefreshToken refreshToken = RefreshToken.builder()
                .key(authentication.getName())
                .value(tokenDto.getRefreshToken())
                .build();

        refreshTokenRepository.save(refreshToken);

        httpServletResponse.addHeader("Access_Token", tokenDto.getGrantType() + " " + tokenDto.getAccessToken());
        httpServletResponse.addHeader("Refresh_Token", tokenDto.getRefreshToken());

        return ResponseDto.success("로그인 성공");
    }

    public ResponseDto<?> signout(MemberRequestDto.Signout signout) {
        // 1. Access Token 검증
        if (!jwtProvider.validateToken(signout.getAccessToken())) {
            return ResponseDto.fail(400, "잘못된 요청입니다.","Bad Request");
        }

        // 2. Access Token 에서 User email 을 가져옵니다.
        Authentication authentication = jwtProvider.getAuthentication(signout.getAccessToken());

        // 3. Redis 에서 해당 User email 로 저장된 Refresh Token 이 있는지 여부를 확인 후 있을 경우 삭제합니다.
        if (redisTemplate.opsForValue().get("RT:" + authentication.getName()) != null) {
            // Refresh Token 삭제
            redisTemplate.delete("RT:" + authentication.getName());
        }

        // 4. 해당 Access Token 유효시간 가지고 와서 BlackList 로 저장하기
        Long expiration = jwtProvider.getExpiration(signout.getAccessToken());
        redisTemplate.opsForValue()
                .set(signout.getAccessToken(), "signout", expiration, TimeUnit.MILLISECONDS);

        return ResponseDto.success("로그아웃 되었습니다.");
    }

}
