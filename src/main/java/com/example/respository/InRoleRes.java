package com.example.respository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entity.RoleEntity;

public interface InRoleRes extends JpaRepository<RoleEntity, Integer> {
      
	RoleEntity findOneByName(String name);
}
