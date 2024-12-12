package com.example.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.blog.entity.Filename;

public interface FilenameRepository extends JpaRepository<Filename, Integer>{
    public Filename findByContentId(Integer contentId);
}
