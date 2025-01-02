package com.example.blog.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.blog.dto.image.ImageListDTO;
import com.example.blog.entity.Images;

public interface ImagesRepository extends JpaRepository<Images , Integer>{
    
    @Query(value = "SELECT new com.example.blog.dto.image.ImageListDTO(i.id , i.title , i.imageUrl) FROM Images i")
    List<ImageListDTO> findAllImages();
}
