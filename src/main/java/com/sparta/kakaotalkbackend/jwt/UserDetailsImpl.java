package com.sparta.kakaotalkbackend.jwt;

import com.sparta.kakaotalkbackend.domain.member.Member;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class UserDetailsImpl implements UserDetails {

	private final Member member;

	public UserDetailsImpl(Member member) { this.member = member; }

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() { return Collections.emptyList(); }


	public Member getMember() {
		return member;
	}

	@Override
	public String getPassword() { return member.getPassword(); }

	@Override
	public String getUsername() {
		return member.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() { return true; }

	@Override
	public boolean isAccountNonLocked() { return true; }

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}