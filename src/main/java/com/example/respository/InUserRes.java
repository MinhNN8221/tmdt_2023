package com.example.respository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.entity.UserEntity;

public interface InUserRes extends JpaRepository<UserEntity, Integer> {
    	
	UserEntity findOneByEmail(String email);
	
	@Query("SELECT e FROM UserEntity e WHERE e.email = :email ")
	Optional<UserEntity> findByEmail(String email);
	
	@Query("SELECT e.email FROM UserEntity e")
	List<String> getEmails();
	
	UserEntity findOneById(Integer id);
	UserEntity findByToken(String token);
}
