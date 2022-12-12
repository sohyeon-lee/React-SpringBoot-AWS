package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("test")
public class TestController {
	
	@GetMapping
	public String testController() {
		return "Hello World!";
	}

	@GetMapping("/testGetMapping")
	public String testControllerWithPath() {
		return "Hello World! testGetMapping ";
	}
	
	@GetMapping("/{id}")
	public String testControllerWithPathVariable(@PathVariable(required = false) int id) {
		//  @PathVariable : /{id}와 같이 URI의 경로로 넘어오는 값을 변수로 받아 올 수 있다.
		// required false : 매개변수는 필수가 아니다.
		return "Hello World! ID" + id;
	}

	@GetMapping("/testRequestParam")
	public String testControllerRequestParam(@RequestParam(required = false) int id) {
		//  @RequestParam : ?id={id}와 같이 요청 매개변수로 넘어오는 값을 변수로 받아올 수 있다.
		// required false : 매개변수는 필수가 아니다.
		return "Hello World! ID" + id;
	}
}
