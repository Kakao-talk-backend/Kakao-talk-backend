package com.sparta.kakaotalkbackend.domain.member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberResponseDto {

	private Long id;
	private String username;
	private String nickname;
	private String image;
	private String status;

	public MemberResponseDto(Member member) {
		this.id = member.getId();
		this.username = member.getUsername();
		this.nickname = member.getNickname();
		this.image =  member.getImage();
		this.status = member.getStatus();
	}

}
