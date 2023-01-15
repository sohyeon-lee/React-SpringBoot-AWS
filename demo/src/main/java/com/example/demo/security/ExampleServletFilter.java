package com.example.demo.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.StringUtils;

/**
 * ServletFilter 예제 (실제로 사용하지 않음)
 * ServletFilter는 dispatcher 서블릿 실행 전에 실행되는 클래스들이다. 
 * 구현된 로직에 따라 원하지 않는 HTTP 요청을 걸러낼 수 있다.
 * 
 * @author isohyeon
 *
 */
public class ExampleServletFilter extends HttpFilter {
	private TokenProvider tokenProvider;
	
	/**
	 *	Filter 구현
	 *	Bearer 토큰을 가져와 TokenProvider를 이용해 사용자를 인증한다. 
	 *	인증이 완료되면 다음 서블릿 필터를 실행하고, 아니라면 HttpServletResponse의 status를 403 Forbidden으로 바꾼다. 
	 *	예외가 나는 경우 디스패처 서블릿을 실행하지 않고 리턴한다.
	 */
	@Override
	protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		try {
			final String token = parseBearerToken(request);
			
			if(token != null && !token.equalsIgnoreCase("null")) {
				// userId 가져오기. 위조된 경우 예외 처리된다.
				String userId = tokenProvider.validateAndGetUserId(token);
				
				// 다음 ServletFilter 실행
				chain.doFilter(request, response);
			}
		} catch (Exception e) {
			// 예외 발생시 response를 403 Forbidden으로 설정.
			// 403 : 서버 자체 또는 서버에 있는 파일에 접근할 권한이 없을 경우에 발생.
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
		}
	}
	
	/**
	 * Http 요청의 헤더를 파싱해 Bearer 토큰을 리턴하는 메서드
	 * 
	 * @param request HTTP 요청
	 * @return Bearer 토큰
	 */
	private String parseBearerToken(HttpServletRequest request) {
		// Http 요청의 헤더를 파싱해 Bearer 토큰을 리턴한다.
		String bearerToken = request.getHeader("Authorization");
		
		if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer")) {
			return bearerToken.substring(7);
		}
		return null;
	}
}
