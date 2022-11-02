package com.sparta.kakaotalkbackend.service;

import com.sparta.kakaotalkbackend.domain.ResponseDto;
import com.sparta.kakaotalkbackend.domain.member.Member;
import com.sparta.kakaotalkbackend.domain.member.MemberResponseDto;
import com.sparta.kakaotalkbackend.domain.member.ProfileUpdateRequest;
import com.sparta.kakaotalkbackend.repository.MemberRepository;
import com.sparta.kakaotalkbackend.util.AmazonS3ResourceStorage;
import com.sparta.kakaotalkbackend.util.MultipartUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final AmazonS3ResourceStorage amazonS3ResourceStorage;
    private final MemberRepository memberRepository;

    /*
    마이 프로필 조회
     */
    public ResponseDto<MemberResponseDto> getMyProfile(Member member) {
        return ResponseDto.success(new MemberResponseDto(member));
    }

    /*
    마이 프로필 수정
     */
    public ResponseDto<MemberResponseDto> updateMyProfile(String nickname,
                                                          String status,
                                                          MultipartFile multipartFile,
                                                          Member member) {

        String image = "";
        if (multipartFile.isEmpty()) {
            image = member.getImage();
            member.update(nickname, status);
        } else {
            image = MultipartUtil.createPath(multipartFile);
            amazonS3ResourceStorage.store(image, multipartFile);
            member.update(nickname, status, image);
        }

        memberRepository.save(member);
        return ResponseDto.success(new MemberResponseDto(member));
    }
}
