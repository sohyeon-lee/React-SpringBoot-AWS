package com.example.demo.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 토큰 생성 후 redirect 해야하는 url을 쿠키에 저장한다.
 *
 * 이 필터는 OAuth 2.0 로직으로 redirection 되기 전에 실행된다.
 * /auth/authorize 경로의 요청인 경우 redirect_url 매개변수의 값을 가져와 response의 쿠키에 추가한다.
 */
@Slf4j
@Component
public class RedirectUrlCookieFilter extends OncePerRequestFilter {
    /**
     * The constant REDIRECT_URI_PARAM.
     */
    public static final String REDIRECT_URI_PARAM = "redirect_url";
    private static final int MAX_AGE = 180;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if(request.getRequestURI().startsWith("/auth/authorize")) {
            try {
                log.info("request url {} ", request.getRequestURI());
                String redirectUrl = request.getParameter(REDIRECT_URI_PARAM); // 리퀘스트 파라미터에서 redirect_url을 가져온다.

                Cookie cookie = new Cookie(REDIRECT_URI_PARAM, redirectUrl);
                cookie.setPath("/");
                cookie.setHttpOnly(true);
                cookie.setMaxAge(MAX_AGE);
                response.addCookie(cookie);
            } catch (Exception e) {
                log.error("Could not set user authentication in security context", e);
                log.info("Unauthorized request");
            }
        }
        filterChain.doFilter(request, response);
    }
}
