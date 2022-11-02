package com.sparta.kakaotalkbackend.controller;

import com.sparta.kakaotalkbackend.domain.ResponseDto;
import com.sparta.kakaotalkbackend.domain.friend.FriendRequestDto;
import com.sparta.kakaotalkbackend.domain.friend.FriendResponseDto;
import com.sparta.kakaotalkbackend.domain.member.MemberResponseDto;
import com.sparta.kakaotalkbackend.jwt.UserDetailsImpl;
import com.sparta.kakaotalkbackend.service.FriendService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class FriendController {
    private final FriendService friendService;

//    // 친구 검색
//    @PostMapping("/friends-list")
//    public ResponseDto<List<FriendResponseDto>> createComment(@RequestBody FriendRequestDto requestDto) {
//        return friendService.searchFriend(requestDto);
//    }
//
//    // 친구 추가
//    @PostMapping("/friends/{friendId}")
//    public ResponseDto<FriendResponseDto> addFriend(@AuthenticationPrincipal UserDtailsImpl userDtails, @PathVariable Long friendId) {
//        return friendService.addFriend(userDtails.getMember(), friendId);
//    }

    // 친구 추가+검색
    @PostMapping("/friends")
    public ResponseDto<FriendResponseDto> addFriend(@AuthenticationPrincipal UserDetailsImpl userDtails, @RequestBody FriendRequestDto requestDto) {
        return friendService.addFriend(userDtails.getMember(), requestDto);
    }

    // 친구 리스트 조회
    @GetMapping("/friends")
    public ResponseDto<List<MemberResponseDto>> getAllFriends(@AuthenticationPrincipal UserDetailsImpl userDtails) {
        return friendService.getAllFriends(userDtails.getMember());
    }

    // 친구 프로필 조회
    @GetMapping("/friends/{friendId}")
    public ResponseDto<MemberResponseDto> getFriend(@PathVariable Long friendId) {
        return friendService.getFriend(friendId);
    }

}
