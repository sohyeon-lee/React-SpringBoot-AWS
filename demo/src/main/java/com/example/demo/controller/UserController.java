package com.example.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.ResponseDTO;
import com.example.demo.dto.UserDTO;
import com.example.demo.model.UserEntity;
import com.example.demo.security.TokenProvider;
import com.example.demo.service.UserService;

import lombok.extern.slf4j.Slf4j;

/**
 * UserService 클래스를 이용해 로그인 기능과 회원가입 기능을 제공하는 Controller 클래스
 * 
 * @author isohyeon
 *
 */
@Slf4j
@RestController
@RequestMapping("/auth")
public class UserController {
	
	private final UserService userService;
	private final TokenProvider tokenProvider;

	public UserController(UserService userService, TokenProvider tokenProvider) {
		this.userService = userService;
		this.tokenProvider = tokenProvider;
	}
	
	/**
	 * 회원가입
	 * 
	 * 반드시 password가 존재하는지 확인한다. 
	 * OAtuh 구현을 위해 데이터베이스에서 password를 notnull로 설정하지 않았기 때문에 패스워드 없이 유저네임만 저장될 수 있다.
	 * 
	 * @param userDTO 회원가입 정보를 담은 UserDTO 클래스
	 * @return http status와 응답 데이터(등록한 userDTO 또는 에러 메시지)를 반환
	 */
	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@RequestBody UserDTO userDTO) {
		try {
			if(userDTO == null || userDTO.getPassword() == null) {
				throw new RuntimeException("Invalid Password value");
			}
			// 요청을 이용해 저장할 유저 만들기
			UserEntity user = UserEntity.builder()
					.username(userDTO.getUsername())
					.password(userDTO.getPassword())
					.build();
			// 서비스를 이용해 레포지터리에 유저 저장
			UserEntity registeredUser = userService.create(user);
			// 유저 정보는 항상 하나이므로 리스트로 만들어야 하는 ResponseDTO를 사용하지 않고 그냥 UserDTO 리턴
			UserDTO responseUserDTO = UserDTO.builder()
					.id(registeredUser.getId())
					.username(registeredUser.getUsername())
					.build();
			return ResponseEntity.ok().body(responseUserDTO);
		} catch (Exception e) {
			ResponseDTO response = ResponseDTO.builder().error(e.getMessage()).build();
			return ResponseEntity.badRequest().body(response);
		}
	}
	
	/**
	 * 로그인
	 * 
	 * @param userDTO 로그인 정보를 담은 UserDTO 클래스
	 * @return http status와 응답 데이터(로그인한 userDTO 또는 에러 메시지)를 반환
	 */
	@PostMapping("/signin")
	public ResponseEntity<?> authenticate(@RequestBody UserDTO userDTO) {
		UserEntity user = userService.getByCredentials(userDTO.getUsername(), userDTO.getPassword());
		
		if(user != null) {
			// 토큰 생성
			final String token = tokenProvider.create(user);
			final UserDTO responUserDTO = UserDTO.builder()
					.username(user.getUsername())
					.id(user.getId())
					.token(token)
					.build();
			return ResponseEntity.ok().body(responUserDTO);
		} else {
			ResponseDTO responseDTO = ResponseDTO.builder().error("Login failed.").build();
			return ResponseEntity.badRequest().body(responseDTO);
		}
	}
}
