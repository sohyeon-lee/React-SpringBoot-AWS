package com.example.demo.dto;

import com.example.demo.model.TodoEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * TodoEntity의 DTO 클래스
 * 
 * 데이터 전달 목적의 DTO 클래스이다. 
 * 비즈니스 로직 캡슐화를 위해서 서비스가 요청을 처리하고 클라이언트로 반환할 때, Model/Entity를 그대로 리턴하지 않고 DTO로 변환하여 리턴한다.
 * 또, 클라이언트가 필요한 정보를 Model/Entity가 포함하지 않은 경우 DTO에 필드로 선언하여 사용한다.
 * 
 * @author isohyeon
 *
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class TodoDTO {
	private String id;
	private String title;
	private boolean done;
	// userid 필드 x : 스프링 시큐리티를 이용해 인증 구현 예정
	// 유저가 자기 아이디를 넘겨주지 않아도 인증 가능
	// userId는 애플리케이션과 데이터베이스에서 사용자를 구별하기 위한 고유 식별자로 사용하기 때문에 숨기는 것이 보안상 좋다.
	
	public TodoDTO(final TodoEntity entity) {
		this.id = entity.getId();
		this.title = entity.getTitle();
		this.done = entity.isDone();
	}
	
	public static TodoEntity toEntity(final TodoDTO dto) {
		return TodoEntity.builder().id(dto.getId()).title(dto.getTitle()).done(dto.isDone()).build();
	}
}
