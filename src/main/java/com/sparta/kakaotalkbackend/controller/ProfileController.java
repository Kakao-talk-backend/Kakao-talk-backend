package com.sparta.kakaotalkbackend.controller;

import com.sparta.kakaotalkbackend.domain.ResponseDto;
import com.sparta.kakaotalkbackend.domain.member.MemberResponseDto;
import com.sparta.kakaotalkbackend.domain.member.ProfileUpdateRequest;
import com.sparta.kakaotalkbackend.jwt.UserDetailsImpl;
import com.sparta.kakaotalkbackend.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/myprofile")
public class ProfileController {

    private final ProfileService profileService;

    @GetMapping
    public ResponseDto<MemberResponseDto> getMyProfile(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return profileService.getMyProfile(userDetails.getMember());
    }

    @PutMapping
    public ResponseDto<MemberResponseDto> updateMyProfile(@RequestPart String nickname,
                                                          @RequestPart String status,
                                                          @RequestPart(value = "file", required = false) MultipartFile multipartFile,
                                                          @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return profileService.updateMyProfile(nickname, status, multipartFile, userDetails.getMember());
    }


}
