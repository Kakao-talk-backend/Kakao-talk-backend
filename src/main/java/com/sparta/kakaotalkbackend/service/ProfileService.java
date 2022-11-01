package com.sparta.kakaotalkbackend.service;

import com.sparta.kakaotalkbackend.domain.ResponseDto;
import com.sparta.kakaotalkbackend.domain.member.Member;
import com.sparta.kakaotalkbackend.domain.member.MemberResponseDto;
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


    /*
    마이 프로필 조회
     */
    public ResponseDto<MemberResponseDto> getMyProfile(Member member) {
        return ResponseDto.success(new MemberResponseDto(member));
    }

    /*
    마이 프로필 수정
     */
    @Transactional
    public ResponseDto<MemberResponseDto> updateMyProfile(String nickname,
                                                          String status,
                                                          MultipartFile multipartFile,
                                                          Member member) {


        if (nickname == null) nickname = member.getNickname();
        if (status == null) status = member.getStatus();

        String image = MultipartUtil.createPath(multipartFile);
        if (image == null) image = member.getImage();
        amazonS3ResourceStorage.store(image, multipartFile);

        member.update(nickname, status, image);
        return ResponseDto.success(new MemberResponseDto(member));
    }
}
