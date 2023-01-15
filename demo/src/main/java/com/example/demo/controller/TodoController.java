package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.ResponseDTO;
import com.example.demo.dto.TodoDTO;
import com.example.demo.model.TodoEntity;
import com.example.demo.service.TodoService;

/**
 * 요청한 Todo 아이템과 http 응답을 반환하는 Controller 클래스
 * 
 * 서비스 로직을 캡슐화하거나 추가적인 정보를 함께 반한하기 위해 DTO를 사용한다.
 * 사용자에게서 TodoDTO를 요청 body로 받고, 이를 TodoEntity로 변환해 저장한다.
 * TodoService의 메서드가 반환한 TodoEntity는 TodoDTO로 변환해서 반환한다.
 * 
 * @author isohyeon
 *
 */
@RestController
@RequestMapping("todo")
public class TodoController {
	
	@Autowired
	private TodoService service;
	
	/**
	 * 레이어드 아키텍처 연습을 위한 테스트 메서드
	 * @return http status와 응답 데이터 반환
	 */
	@GetMapping("/test")
	public ResponseEntity<?> testTodo() {
		String str = service.testService();
		List<String> list = new ArrayList<>();
		list.add(str);
		ResponseDTO<String> response = ResponseDTO.<String>builder().data(list).build();
		return ResponseEntity.ok().body(response);
	}
	
	/**
	 * Todo 아이템 추가 메서드
	 * @param userId 인증된 유저 아이디 (현재 로그인 중인 유저)
	 * @param dto 사용자가 추가하려는 json 형식의 데이터를 담은 TodoDTO
	 * @return http status와 todo list를 반환
	 */
	@PostMapping
	public ResponseEntity<?> createTodo(@AuthenticationPrincipal String userId, @RequestBody TodoDTO dto) {
		try {
			// TodoEntity로 변환한다.
			TodoEntity entity = TodoDTO.toEntity(dto);
			// id를 null로 초기화 한다. 생성 당시에는 id가 없어야 하기 때문이다.
			entity.setId(null);
			// 인증된 유저만 로그인 없이 사용 가능
			entity.setUserId(userId);
			
			// 서비스를 이용해 Todo엔티티를 생성한다.
			List<TodoEntity> entities = service.create(entity);
			
			// 자바 스트림을 이용해 리턴된 엔티티 리스트를 TodoDTO리스트로 변환
			List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());

			// 변환된 TodoDTO리스트를 이용해 ResponseDTO를 초기화한다.
			ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();
			return ResponseEntity.ok().body(response);
			
		} catch (Exception e) {
			// 혹시 예외가 나는 경우 dto대신 error에 메시지를 넣어 리턴한다.
			String error = e.getMessage();
			ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().error(error).build();
			return ResponseEntity.badRequest().body(response);
		}
	}
	
	/**
	 * Todo 아이템 목록 조회 메서드
	 * @param userId 인증된 유저 아이디 (현재 로그인 중인 유저)
	 * @return http status와 todo list를 반환
	 */
	@GetMapping
	public ResponseEntity<?> retrieveTodoList(@AuthenticationPrincipal String userId) {
		// 서비스 클래스의 retrieve 메서드를 사용해 Todo 리스트를 가져온다.
		List<TodoEntity> entities = service.retrieve(userId);
		
		// 자바 스트림을 이용해 리턴된 엔티티 리스트를 TodoDTO리스트로 변환
		List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());

		ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();
		return ResponseEntity.ok().body(response);
	}
	
	/**
	 * Todo 아이템 수정 메서드
	 * @param userId 인증된 유저 아이디 (현재 로그인 중인 유저)
	 * @param dto 사용자가 수정한 json 형식의 데이터를 담은 TodoDTO
	 * @return http status와 todo list를 반환
	 */
	@PutMapping
	public ResponseEntity<?> updateTodo(@AuthenticationPrincipal String userId, @RequestBody TodoDTO dto) {
		TodoEntity entity = TodoDTO.toEntity(dto);
		entity.setUserId(userId); // 수정 예정
		
		List<TodoEntity> entities = service.update(entity);
		List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());

		ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();
		return ResponseEntity.ok().body(response);
	}
	
	/**
	 * Todo 아이템 삭제 메서드
	 * @param userId 인증된 유저 아이디 (현재 로그인 중인 유저)
	 * @param dto 사용자가 삭제하려는 json 형식의 데이터를 담은 TodoDTO
	 * @return http status와 todo list를 반환
	 */
	@DeleteMapping
	public ResponseEntity<?> deleteTodo(@AuthenticationPrincipal String userId, @RequestBody TodoDTO dto) {
		try {
			TodoEntity entity = TodoDTO.toEntity(dto);
			entity.setUserId(userId); // 수정 예정
			
			List<TodoEntity> entities = service.delete(entity);
			List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());
			
			ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();
			return ResponseEntity.ok().body(response);

		} catch (Exception e) {
			String error = e.getMessage();
			ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().error(error).build();
			return ResponseEntity.badRequest().body(response);
		}
	}
}
