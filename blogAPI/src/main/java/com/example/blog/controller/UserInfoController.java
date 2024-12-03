package com.example.blog.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.blog.entity.AdminUser;
import com.example.blog.service.UserInfoService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/api/userinfo")
public class UserInfoController {

	@Autowired
	private UserInfoService userInfoService;
	
	@GetMapping("/alldata")
	public  List<AdminUser> getAllUserDataController() {		
		List<AdminUser> allUserData = userInfoService.getAllUser();
		return allUserData;
	}
}
