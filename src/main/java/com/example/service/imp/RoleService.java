package com.example.service.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.RoleEntity;
import com.example.respository.InRoleRes;
import com.example.service.InRoleService;

@Service
public class RoleService implements InRoleService {

	@Autowired
	private InRoleRes role;
	
	@Override
	public RoleEntity findOneByName(String name) {
		// TODO Auto-generated method stub
		return role.findOneByName(name);
	}

}
