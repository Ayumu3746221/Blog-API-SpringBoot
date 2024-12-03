package com.example.blog.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.blog.dto.PublishedContentListDTO;
import com.example.blog.entity.Content;

@Repository
public interface ContentRepository extends JpaRepository<Content, Integer>{
    
    @Query("SELECT new com.example.blog.dto.PublishedContentListDTO(c.contentId, c.title, c.imageUrl, c.contentUrl, c.updatedAt) FROM Content c WHERE c.isPublished = TRUE")
    List<PublishedContentListDTO> findPublishedArticles();
}
