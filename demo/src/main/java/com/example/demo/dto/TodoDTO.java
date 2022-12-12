package com.example.demo.dto;

import com.example.demo.model.TodoEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
}
