package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.ResponseDTO;
import com.example.demo.dto.TestRequestBodyDTO;

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
	
	@GetMapping(value={"/{id}", "/"}) // @PathVariable 생략을 허용하기 위한 분기
	public String testControllerWithPathVariable(@PathVariable("id") Optional<Integer> id) {
		// @PathVariable : /{id}와 같이 URI의 경로로 넘어오는 값을 변수로 받아 올 수 있다.
		// 생략을 허용하기 위해 Optional을 사용한다.
		return "Hello World! ID" + id;
	}

//	@GetMapping("/testRequestParam")
//	public String testControllerRequestParam(@RequestParam(required = false, defaultValue = "0") int id ) {
//		// @RequestParam : ?id={id}와 같이 요청 매개변수로 넘어오는 값을 변수로 받아올 수 있다.
//		// required false : 매개변수는 필수가 아니다.
//		return "Hello World! ID" + id;
//	}

	@GetMapping("/testRequestParam")
	public String testControllerRequestParam(@RequestParam(required = false) String id ) {
		return "Hello World! ID" + id;
	}
	
	// TestRequestBodyDTO를 요청 바디로 받는 메서드
	@GetMapping("/testRequestBody")
	public String testControllerRequestBody(@RequestBody TestRequestBodyDTO testRequestBody) {
		return "Hello World! ID:" + testRequestBody.getId() + " Message:" + testRequestBody.getMessage();
	}

	@GetMapping("/testResponseBody")
	public ResponseDTO<String> testControllerResponseBody() {
		List<String> list = new ArrayList<>();
		list.add("Hello World!");
		list.add("I'm ResponseDTO");
		ResponseDTO<String> response = ResponseDTO.<String>builder().data(list).build();
		return response;
	}
	
	@GetMapping("/testResponseEntity")
	public ResponseEntity<?> testControllerResponseEntity() {
		List<String> list = new ArrayList<>();
		list.add("Hello World! I'm ResponseEntity. And you got 400!");
		ResponseDTO<String> response = ResponseDTO.<String>builder().data(list).build();
		// http status를 400로 설정.
		return ResponseEntity.badRequest().body(response);
	}
}
