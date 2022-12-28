package com.example.demo.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import lombok.extern.slf4j.Slf4j;

/**
 * JwtAuthenticationFilter 클래스
 * 
 * Spring이 관리하는 Spring Security Filter이다. 
 * HttpFilter가 아닌 OncePerRequestFilter를 상속하고, web.xml 대신 webSecurityConfigurerAdapter라는 클래스를 상속해 필터를 설정한다.
 * 
 * OncePerRequestFilter는 한 요청당 반드시 한 번만 실행된다.
 * 인증 부분만 구현하고 유효 시간 검사는 생략했다. (추가 예정)
 * 
 * @author isohyeon
 *
 */
@Slf4j
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
	
	private final TokenProvider tokenProvider;

	public JwtAuthenticationFilter(TokenProvider tokenProvider) {
		this.tokenProvider = tokenProvider;
	}

	/**
	 *	Filter 구현
	 *	Authorization 토큰을 가져와 TokenProvider를 이용해 사용자를 인증한다. 
	 */
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		try {
			// 요청에서 토큰 가져오기.
			String token = parseBearerToken(request);
			log.info("Filter is running...");
			// 토큰 검사하기. JWT이므로 인가 서버에 요청하지 않고도 검증 가능.
			if(token != null && !token.equalsIgnoreCase("null")) {
				// userId 가져오기. 위조된 경우 예외 처리된다.
				String userId = tokenProvider.validateAndGetUserId(token);
				log.info("Authenticated user ID : " + userId);
				// 인증 완료. SecurityContextHolder에 등록해야 인증된 사용자라고 생각한다.
				AbstractAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
						userId, // AuthenticationPrincipal(principal). 인증된 사용자의 정보. 문자열이 아니어도 아무거나 넣을 수 있다. 보통 UserDetails라는 오브젝트를 넣는데, 나는 안 만들었음.
						null, 	// 패스워드. 보안상 노출을 방지하기 위해 null로 설정한다.
						AuthorityUtils.NO_AUTHORITIES // ?
				);
				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				
				SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
				securityContext.setAuthentication(authentication);
				
				SecurityContextHolder.setContext(securityContext);
			}
		} catch (Exception e) {
			logger.error("Could not set user authentication in security context", e);
		}
		
		filterChain.doFilter(request, response);
	}
	
	/**
	 * Http 요청의 헤더를 파싱해 Authorization 토큰을 리턴하는 메서드
	 * 
	 * @param request HTTP 요청
	 * @return Bearer 토큰
	 */
	private String parseBearerToken(HttpServletRequest request) {
		// Http 요청의 헤더를 파싱해 Bearer 토큰을 리턴한다.
		String bearerToken = request.getHeader("Authorization");
		
		if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer")) {
			return bearerToken.substring(6);
		}
		return null;
	}
}
