package com.sparta.kakaotalkbackend.service;

import com.sparta.kakaotalkbackend.domain.ResponseDto;
import com.sparta.kakaotalkbackend.domain.member.Member;
import com.sparta.kakaotalkbackend.domain.member.MemberResponse;
import com.sparta.kakaotalkbackend.domain.member.ProfileUpdateRequest;
import com.sparta.kakaotalkbackend.repository.MemberRepository;
import com.sparta.kakaotalkbackend.util.Check;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final MemberRepository memberRepository;
    private final Check check;

    /*
    마이 프로필 조회
     */
    public ResponseDto<MemberResponse> getMyProfile(Member member) {

        check.memberExist(member);
        return ResponseDto.success(new MemberResponse(member));

    }

    /*
    마이 프로필 수정
     */
    @Transactional
    public ResponseDto<MemberResponse> updateMyProfile(ProfileUpdateRequest profileUpdateRequest, Member member) {
        check.memberExist(member);

        member.update(profileUpdateRequest);

        return ResponseDto.success(new MemberResponse(member));
    }
}
