package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.filter.CorsFilter;

import com.example.demo.security.JwtAuthenticationFilter;

import lombok.extern.slf4j.Slf4j;

/**
 * 스프링 시큐리티 설정 클래스
 * 서블릿 컨테이너에게 특정 서블릿 필터를 사용하라고 알려주는 설정 작업을 한다.
 * 
 * @author isohyeon
 *
 */
@EnableWebSecurity
@Slf4j
public class WebSecurityConfig {
	
	private JwtAuthenticationFilter jwtAuthenticationFilter;

	public WebSecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
		this.jwtAuthenticationFilter = jwtAuthenticationFilter;
	}
	
	/**
	 * 시큐리티 관련 설정
	 * 
	 * HttpSecurity의 빌더를 이용해 cors, csrf, httpbasic, session, authorizeRequest 등 다양한 설정을 할 수 있다.
	 * 즉, web.xml 대신 HttpSecurity를 이용해 시큐리티 관련 설정을 할 수 있다.
	 * addFilterAfter 메서드를 통해 AuthenticationFilter를 CorsFilter 이후에 실행하도록 설정하였다.
	 * 
	 * @param http 시큐리티 설정을 위한 객체
	 * @return DefaultSecurityFilterChain 
	 * @throws Exception
	 */
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		// http 시큐리티 빌더
		http.cors() // WebMvcConfig에서 이미 설정했으므로 기본 cors 설정.
				.and()
				.csrf()
				.disable() // csrf는 현재 사용하지 않으므로 disable
				.httpBasic()
				.disable() // token을 사용하므로 basic 인증 disable
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // session 기반이 아님
				.and()
				.authorizeRequests()
				.antMatchers("/", "/auth/**", "/oauth2/**").permitAll() // /와 /auth/** 경로는 인증 안해도 됨
				.anyRequest()
				.authenticated() // 이외의 모든 경로는 인증해야 함
				.and()
				.oauth2Login()
				.redirectionEndpoint()
				.baseUri("/oauth2/callback/*");
		
		// filter 등록
		// 매 요청마다 CorsFilter 실행한 후에 jwtAuthenticationFilter 실행
		http.addFilterAfter(jwtAuthenticationFilter, CorsFilter.class);
		
		return http.build();
	}
	
    /**
     * 패스워드 암호화
     * 빈 등록을 위해 작성함
     * @return BCryptPasswordEncoder
     */
    @Bean
    public BCryptPasswordEncoder encodePassword() {
        return new BCryptPasswordEncoder();
    }
}
