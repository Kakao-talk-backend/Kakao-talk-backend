package com.sparta.kakaotalkbackend.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.ObjectUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class JwtFilter extends GenericFilter {

	private final JwtProvider jwtProvider;
	private final RedisTemplate redisTemplate;


	public JwtFilter(JwtProvider jwtProvider, RedisTemplate redisTemplate) {
		this.jwtProvider = jwtProvider;
		this.redisTemplate = redisTemplate;
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

		// 1. Request Header 에서 토큰을 꺼냄
		String jwt = jwtProvider.resolveToken((HttpServletRequest) request);

		// 2. validateToken 으로 토큰 유효성 검사
		if (jwt != null && jwtProvider.validateToken(jwt)) {
			// Redis(블랙리스트)에서 해당 accessToken signout 여부 확인
			String isSignout = (String)redisTemplate.opsForValue().get(jwt);
			// Redis(블랙리스트)에 없을 경우
			if (ObjectUtils.isEmpty(isSignout)) {
				// 토큰이 유효할 경우 토큰에서 Authentication 객체를 가지고 와서 SecurityContext 에 저장
				Authentication auth = jwtProvider.getAuthentication(jwt);
				SecurityContextHolder.getContext().setAuthentication(auth);
			}
		}

		chain.doFilter(request, response);
	}
}

