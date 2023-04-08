package com.example.service;

import com.example.entity.RoleEntity;

public interface InRoleService {
       
	RoleEntity findOneByName(String name);
}
