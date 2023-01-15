package com.example.demo.security;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 소셜 로그인 인증이 완료된 다음 토큰 생성 및 반환
 * <p>
 * OAuth 2.0 로직이 완료된 후 호출된다.
 * TokenProvider를 통해 토큰을 생성하고 HttpServletResponse에 토큰을 담는다.
 */
@Slf4j
@Component
@AllArgsConstructor
public class OAuthSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        TokenProvider tokenProvider = new TokenProvider();
        String token = tokenProvider.create(authentication);

        log.info("token {}", token);
        //response.getWriter().write(token);
        response.sendRedirect("http://localhost:3000/sociallogin?token=" + token);
    }
}
