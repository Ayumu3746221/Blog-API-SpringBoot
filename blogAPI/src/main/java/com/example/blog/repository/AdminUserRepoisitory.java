package com.example.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.blog.entity.AdminUser;

public interface AdminUserRepoisitory extends JpaRepository<AdminUser, Integer>{

}
