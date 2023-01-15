package com.example.demo.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.UserEntity;

/**
 * User 테이블 접근을 위한 Repository 클래스
 * <p>
 * 필요한 쿼리를 작성하고, 데이터베이스에서 반환된 데이터를 entity 객체로 변환하여 반환한다.
 * JpaRepository<T, ID>를 상속받는다.
 * T: 테이블에 매핑할 엔티티 클래스
 * ID: 엔티티의 기본 키 타입
 *
 * @author isohyeon
 */
@Repository
public interface UserRepository extends JpaRepository<UserEntity, String> {
	UserEntity findByUsername(String username);
	Boolean existsByUsername(String username);
	UserEntity findByUsernameAndPassword(String username, String password);
}
