package com.example.demo.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.GenericGenerator;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * User 테이블과 매핑되는 엔티티 클래스
 * 
 * 테이블의 컬럼과 동일하게 매핑되도록 필드를 선언해야 한다.
 * OAuth를 이용한 SSO 구현을 위해 password는 null을 허용한다.
 * 
 * @author isohyeon
 *
 */
@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user", uniqueConstraints = {@UniqueConstraint(columnNames="username")})
public class UserEntity {
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	private String id; 				// 유저 고유 id
	private String username;		// 아이디로 사용할 유저 이름 (이메일이나 일반 문자열)
	private String password;		// 패스워드
	private String role;			// 사용자의 역할 (ex. 관리자, 일반 사용자)
	private String authProvider;	// OAuth에서 사용할 유저 정보 제공자
}
