package com.example.demo.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.model.UserEntity;
import com.example.demo.persistence.UserRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * User에 대한 비즈니스 로직을 수행하는 Service 클래스
 * 
 * @author isohyeon
 *
 */
@Slf4j
@Service
public class UserService {
	
	private final UserRepository userRepository;

	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	/**
	 * 사용자 생성 메서드(회원가입)
	 * userEntity 값이 null이거나 이미 존재할 경우 RuntimeException이 발생한다.
	 * 
	 * @param userEntity 등록할 user 정보를 담은 엔티티 클래스
	 * @return UserRepository 클래스의 save 메서드 결과 반환
	 */
	public UserEntity create(final UserEntity userEntity) {
		if(userEntity == null || userEntity.getUsername() == null) {
			throw new RuntimeException("invalid arguments");
		}
		final String username = userEntity.getUsername();
		if(userRepository.existsByUsername(username)) {
			log.warn("Username already exists {}", username);
			throw new RuntimeException("Username already exists");
		}
		
		return userRepository.save(userEntity);
	}
	
	/**
	 * 로그인 인증 메서드
	 * 
	 * @param username 로그인 대상의 username
	 * @param password 로그인 대상의 password
	 * @param encoder 스프링 시큐리티가 제공하는 BcryptPasswordEncoder
	 * @return 로그인 유저 정보를 담은 UserEntity 또는 null
	 */
	public UserEntity getByCredentials(final String username, final String password, final PasswordEncoder encoder) {
		final UserEntity originalUser = userRepository.findByUsername(username);
		
		// matches 메서드를 이용해 패스워드가 같은지 확인
		if(originalUser != null && encoder.matches(password, originalUser.getPassword())) {
			return originalUser;
		}
		return null;
	}
}
