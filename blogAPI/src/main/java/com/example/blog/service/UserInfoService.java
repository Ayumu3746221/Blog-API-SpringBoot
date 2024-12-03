package com.example.blog.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.blog.entity.AdminUser;
import com.example.blog.repository.AdminUserRepoisitory;

@Service
public class UserInfoService {

	@Autowired
	private AdminUserRepoisitory adminUserRepoisitory;
	
	public List<AdminUser> getAllUser() {
		List<AdminUser> usersData= adminUserRepoisitory.findAll();
		System.out.println("Users from service: " + usersData);
		return usersData;
	}
}
