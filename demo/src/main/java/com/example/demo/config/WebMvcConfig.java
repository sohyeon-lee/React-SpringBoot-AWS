package com.example.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * CORS 설정을 위한 클래스
 * 
 * @author isohyeon
 *
 */
@Configuration // 스프링 빈으로 등록
public class WebMvcConfig implements WebMvcConfigurer {
	private final long MAX_AGE_SECS = 3600;
	
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**") // 모든 경로에 대해
			.allowedOrigins("http://localhost:3000", "http://192.168.0.4:3000") // Origin이 http://localhost:3000인 경우
			.allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS") // GET, POST, PUT, PATCH, DELETE, OPTIONS 메서드 요청을 허용한다.
			.allowedHeaders("*") // 모든 헤더와
			.allowCredentials(true) // 인증에 관한 정보도 허용 한다.
			.maxAge(MAX_AGE_SECS);
	}
}
