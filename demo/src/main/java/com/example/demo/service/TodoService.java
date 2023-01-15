package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.TodoEntity;
import com.example.demo.persistence.TodoRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * Todo 아이템에 대한 비즈니스 로직을 수행하는 Service 클래스
 * 
 * controller와 persistence 사이에서 비즈니스 로직을 수행한다.
 * HTTP와 연관된 컨트롤러에서 분리돼 있고, 데이터베이스와 연관된 퍼시스턴스와도 분리돼 있다.
 * 서비스 레이어에서는 우리가 개발하고자 하는 로직에 집중할 수 있다.
 * 
 * @author isohyeon
 *
 */
@Slf4j
@Service
public class TodoService {
	
	@Autowired
	private TodoRepository repository;
	
	/**
	 * 레이어드 아키텍처 연습을 위한 테스트 메서드
	 * @return
	 */
	public String testService() {
		TodoEntity entity = TodoEntity.builder().title("My first todo item").build();
		repository.save(entity);
		TodoEntity savedEntity = repository.findById(entity.getId()).get(); // findById JPA 제공 메서드
		
		return savedEntity.getTitle();
	}
	
	/**
	 * Todo 아이템 추가 메서드
	 * @param entity 추가 데이터를 담은 TodoEntity 클래스
	 * @return TodoRepository 클래스의 findByUserId 메서드 결과 반환
	 */
	public List<TodoEntity> create(final TodoEntity entity) {
		validate(entity); 

		repository.save(entity);
		log.info("Entity Id : {} is saved.", entity.getId());
		return repository.findByUserId(entity.getUserId());
	}
	
	/**
	 * Todo 아이템 목록 조회 메서드
	 * @param userId 조회하려는 todo 작성자 아이디
	 * @return TodoRepository클래스의 findByUserId메서드 결과 반환
	 */
	public List<TodoEntity> retrieve(final String userId) {
		return repository.findByUserId(userId);
	}
	
	/**
	 * Todo 아이템 수정 메서드
	 * @param entity 수정 데이터 담은 TodoEntity 클래스
	 * @return TodoService클래스의 retrieve메서드 결과 반환
	 */
	public List<TodoEntity> update(final TodoEntity entity) {
		validate(entity);
		
		final Optional<TodoEntity> original = repository.findById(entity.getId());
//		original.ifPresent(todo -> {
//			todo.setTitle(entity.getTitle());
//			todo.setDone(entity.isDone());
//			
//			repository.save(todo);
//		});
		if(original.isPresent()) {
			final TodoEntity todo = original.get();
			todo.setTitle(entity.getTitle());
			todo.setDone(entity.isDone());
			
			repository.save(todo);
		}
		
		return retrieve(entity.getUserId());
	}
	
	/**
	 * Todo 아이템 삭제 메서드
	 * @param entity 삭제하려는 데이터를 담은 TodoEntity 클래스
	 * @return TodoService클래스의 retrieve메서드 결과 반환
	 */
	public List<TodoEntity> delete(final TodoEntity entity) {
		validate(entity);
		
		try {
			repository.delete(entity);
		} catch (Exception e) {
			log.error("error deleting entity", entity.getId(), e);
			throw new RuntimeException("error deleting entity" + entity.getId());
		}
		
		return retrieve(entity.getUserId());
	}
	
	/**
	 * 데이터 검증 메서드
	 * 코드의 크기가 커지면 클래스로 분리할 예정임
	 * @param entity 검증하려는 데이터를 담은 엔티티
	 */
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
