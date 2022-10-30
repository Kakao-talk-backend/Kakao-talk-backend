package com.sparta.kakaotalkbackend.controller;

import com.sparta.kakaotalkbackend.domain.ResponseDto;
import com.sparta.kakaotalkbackend.domain.member.MemberRequestDto;
import com.sparta.kakaotalkbackend.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class MemberController {

	private final MemberService memberService;

	//회원가입
	@PostMapping("/api/signup")
	public ResponseDto<?> signup(@RequestBody @Valid MemberRequestDto memberRequestDto) {
		return memberService.registerUser(memberRequestDto);
	}
}
