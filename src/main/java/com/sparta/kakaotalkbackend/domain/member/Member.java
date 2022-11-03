package com.sparta.kakaotalkbackend.domain.member;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.util.List;

@Builder
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Member {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "member_id")
	private Long id;

	@Column(nullable = false, unique = true)
	private String username;

	@Column(nullable = false)
	private String nickname;

	@Column(nullable = false) // &&&& default 이미지 저장
	private String image;

//	@Column
//	@Convert
//	private List<String> friends;

	@JsonIgnore
	@Column(nullable = false)
	private String password;

	@Column
	private String status;

	public boolean validatePassword(PasswordEncoder passwordEncoder, String password) {
		return passwordEncoder.matches(password, this.password);
	}

    public void update(String nickname, String status, String image) {
        this.nickname = nickname;
        this.image = "https://kang--bucket.s3.ap-northeast-2.amazonaws.com/" + image;
        this.status = status;
    }
}
