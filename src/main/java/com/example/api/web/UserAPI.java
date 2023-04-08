package com.example.api.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.UserEntity;
import com.example.service.InUserService;

@RestController
public class UserAPI {
      
	@Autowired
	private InUserService userService;
	
	@PostMapping(value = "/freshfood/user/update")
	public void update(@RequestBody UserEntity user) {
		System.out.println(user.getFullname()+ " " + user.getEmail());
	}
}
