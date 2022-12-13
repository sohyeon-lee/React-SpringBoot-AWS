package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

@RestController
@RequestMapping("todo")
public class TodoController {
	
	@Autowired
	private TodoService service;
	
	private static String temporaryUserId = "temporary-user"; // 임시 user id
	
	@GetMapping("/test")
	public ResponseEntity<?> testTodo() {
		String str = service.testService();
		List<String> list = new ArrayList<>();
		list.add(str);
		ResponseDTO<String> response = ResponseDTO.<String>builder().data(list).build();
		
		return ResponseEntity.ok().body(response);
	}
	
	@PostMapping
	public ResponseEntity<?> createTodo(@RequestBody TodoDTO dto) {
		try {
			// TodoEntity로 변환한다.
			TodoEntity entity = TodoDTO.toEntity(dto);
			// id를 null로 초기화 한다. 생성 당시에는 id가 없어야 하기 때문이다.
			entity.setId(null);
			// 인증과 인가에서 수정 할 예정, 지금은 인증과 인가 기능이 없으므로 한 유저(temporary-user)만 로그인 없이 사용 가능
			entity.setUserId(temporaryUserId);
			
			// 서비스를 이용해 Todo엔티티를 생성한다.
			List<TodoEntity> entities = service.create(entity);
			
			// 자바 스트림을 이용해 리턴된 엔티티 리스트를 TodoDTO리스트로 변환
			//List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());
			List<TodoDTO> dtos = new ArrayList<>();
			TodoDTO todoDTO = new TodoDTO();
			for(int i = 0; i < entities.size(); i++) {
				todoDTO.setId(entities.get(i).getId());
				todoDTO.setTitle(entities.get(i).getTitle());
				todoDTO.setDone(entities.get(i).isDone());
				dtos.add(todoDTO);
			}
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
	
	@GetMapping
	public ResponseEntity<?> retrieveTodoList() {
		// 서비스 클래스의 retrieve 메서드를 사용해 Todo 리스트를 가져온다.
		List<TodoEntity> entities = service.retrieve(temporaryUserId);
		
		// 자바 스트림을 이용해 리턴된 엔티티 리스트를 TodoDTO리스트로 변환
		//List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());
		List<TodoDTO> dtos = new ArrayList<>();
		TodoDTO todoDTO = new TodoDTO();
		for(int i = 0; i < entities.size(); i++) {
			todoDTO.setId(entities.get(i).getId());
			todoDTO.setTitle(entities.get(i).getTitle());
			todoDTO.setDone(entities.get(i).isDone());
			dtos.add(todoDTO);
		}
		ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();
		
		return ResponseEntity.ok().body(response);
	}
	
	@PutMapping
	public ResponseEntity<?> updateTodo(@RequestBody TodoDTO dto) {
		TodoEntity entity = TodoDTO.toEntity(dto);
		entity.setUserId(temporaryUserId); // 수정 예정
		
		List<TodoEntity> entities = service.update(entity);
		
		//List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());
		List<TodoDTO> dtos = new ArrayList<>();
		TodoDTO todoDTO = new TodoDTO();
		for(int i = 0; i < entities.size(); i++) {
			todoDTO.setId(entities.get(i).getId());
			todoDTO.setTitle(entities.get(i).getTitle());
			todoDTO.setDone(entities.get(i).isDone());
			dtos.add(todoDTO);
		}
		ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();
		
		return ResponseEntity.ok().body(response);
	}
}
