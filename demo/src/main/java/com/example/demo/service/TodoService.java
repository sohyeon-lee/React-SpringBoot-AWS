package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.TodoEntity;
import com.example.demo.persistence.TodoRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * @author isohyeon
 *
 */
@Slf4j
@Service
public class TodoService {
	
	@Autowired
	private TodoRepository repository;
	
	/**
	 * @return
	 */
	public String testService() {
		TodoEntity entity = TodoEntity.builder().title("My first todo item").build();
		repository.save(entity);
		TodoEntity savedEntity = repository.findById(entity.getId()).get(); // findById JPA 제공 메서드
		
		return savedEntity.getTitle();
	}
	
	/**
	 * @param entity
	 * @return
	 */
	public List<TodoEntity> create(final TodoEntity entity) {
		validate(entity); 

		repository.save(entity);
		log.info("Entity Id : {} is saved.", entity.getId());
		return repository.findByUserId(entity.getUserId());
	}
	
	/**
	 * @param userId
	 * @return
	 */
	public List<TodoEntity> retrieve(final String userId) {
		return repository.findByUserId(userId);
	}
	
	/**
	 * @param entity
	 * @return
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
	 * Todo 삭제
	 * 
	 * @param entity 삭제하려는 Todo에 대한 정보를 담은 엔티티
	 * @return 삭제 후 Todo 리스트를 반환 
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
	 * 
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
