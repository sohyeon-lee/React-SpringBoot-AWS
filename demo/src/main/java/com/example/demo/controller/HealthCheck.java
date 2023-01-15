package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 일래스틱 빈스톡이 애플리케이션 상태를 확인하는 용도의 클래스
 * <p>
 * AWS 로드 밸런서는 기본 경로인 ‘/’에 HTTP 요청을 보내 애플리케이션이 동작하는지 확인한다.
 */
@RestController
public class HealthCheck {

    /**
     * AWS 로드 밸런서의 요청 경로
     *
     * @return the string (The Service is up and running ...)
     */
    @GetMapping("/")
    public String healthCheck() {
        return "The Service is up and running ...";
    }
}