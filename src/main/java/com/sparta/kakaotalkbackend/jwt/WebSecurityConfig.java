package com.sparta.kakaotalkbackend.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity // 스프링 Security 지원을 가능하게 함
@RequiredArgsConstructor
public class WebSecurityConfig {

	private final JwtProvider jwtProvider;
	private final RedisTemplate redisTemplate;

	//암호화 하지않으면 security 에서 로그인을 막음
	@Bean
	public BCryptPasswordEncoder encodePassword() { return new BCryptPasswordEncoder(); }

	@Bean
	public WebSecurityCustomizer webSecurityCustomizer() {
		// h2-console 사용에 대한 허용 (CSRF, FrameOptions 무시)
		return (web) -> web.ignoring()
				.antMatchers("/h2-console/**");
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		// CSRF 설정 Disable
		http.csrf().disable();

		http
				// 시큐리티는 기본적으로 세션을 사용
				// 여기서는 세션을 사용하지 않기 때문에 세션 설정을 Stateless 로 설정
				.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and()
				.authorizeRequests()
				// 아래의 경로로 usl 요청이 들어오면 인증된 user만 진입 허용
				.antMatchers("/api/auth/**").permitAll()
				///api/auth/comment
				//api/auth/recomment
				// 나머지 request는 인증없이 허용
				.anyRequest().permitAll()
				.and()
				//JWT 토큰 필터 처리하는 부분
				.addFilterBefore(new JwtFilter(jwtProvider, redisTemplate), UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}
}
