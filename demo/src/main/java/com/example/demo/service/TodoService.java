package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.TodoEntity;
import com.example.demo.persistence.TodoRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TodoService {
	
	@Autowired
	private TodoRepository repository;
	
	public String testService() {
		
		// TodoEntity 생성
		TodoEntity entity = TodoEntity.builder().title("My first todo item").build();
		// TodoEntity 저장
		repository.save(entity);
		// TodoEntity 검색
		TodoEntity savedEntity = repository.findById(entity.getId()).get(); // findById JPA 제공 메서드
		
		return savedEntity.getTitle();
	}
	
	public List<TodoEntity> create(final TodoEntity entity) {
		validate(entity); 

		repository.save(entity);
		log.info("Entity Id : {} is saved.", entity.getId());
		return repository.findByUserId(entity.getUserId());
	}
	
	// 넘어온 데이터가 유효한지 검사, 코드가 더 커지면 클래스로 분리 예정
	private void validate(final TodoEntity entity) {
		if(entity == null) {
			log.warn("Entity cannot be null.");
			throw new RuntimeException("Entity cannot be null.");
		}

		if(entity.getUserId() == null) {
			log.warn("Unknown user.");
			throw new RuntimeException("Unknown user.");
		}
	}
}
