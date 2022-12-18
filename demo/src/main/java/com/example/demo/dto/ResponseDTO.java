package com.example.demo.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * HTTP 응답 시 body로 전달할 데이터를 담는 DTO 클래스
 * 
 * 응답 데이터는 data 필드에 담고, 예외가 나는 경우 error 메시지를 error 필드에 담는다.
 * 다양한 DTO 클래스를 응답 데이터로 처리하기 위해 Generic을 사용함
 * 
 * @author isohyeon
 * @param <T> HTTP 응답으로 반환할 데이터, 데이터 타입 상관 없음
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ResponseDTO<T> {
	private String error;
	private List<T> data;
}
