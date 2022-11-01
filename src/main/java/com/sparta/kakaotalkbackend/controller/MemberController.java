package com.sparta.kakaotalkbackend.controller;

import com.sparta.kakaotalkbackend.domain.ResponseDto;
import com.sparta.kakaotalkbackend.domain.member.Member;
import com.sparta.kakaotalkbackend.domain.member.MemberResponseDto;
import com.sparta.kakaotalkbackend.domain.member.SigninRequestDto;
import com.sparta.kakaotalkbackend.domain.member.MemberRequestDto;
import com.sparta.kakaotalkbackend.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    //회원가입
    @PostMapping("/signup")
    public ResponseDto<String> signup(@RequestPart @Valid String username,
                                                 @RequestPart @Valid String nickname,
                                                 @RequestPart @Valid String password,
                                                 @RequestPart @Valid String passwordCheck,
                                                 @RequestPart @Valid String status,
                                                 @RequestPart(value = "file", required = false) MultipartFile multipartFile) {

        MemberRequestDto requestDto = new MemberRequestDto(username, nickname, status, password, passwordCheck);
        return memberService.registerUser(requestDto, multipartFile);
    }

    // 로그인
    @PostMapping("/signin")
    public ResponseDto<String> signin(@RequestBody SigninRequestDto signinRequestDto, HttpServletResponse httpServletResponse) {
        return memberService.signin(signinRequestDto, httpServletResponse);
    }

    // 로그아웃
    @PostMapping("/signout")
    public ResponseDto<String> logout(@Validated MemberRequestDto.Signout signout) {
        return memberService.signout(signout);
    }

}
