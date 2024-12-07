package com.example.blog.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.blog.dto.AllContentListDTO;
import com.example.blog.dto.ArticleDTO;
import com.example.blog.dto.PublishedContentListDTO;
import com.example.blog.entity.Content;

@Repository
public interface ContentRepository extends JpaRepository<Content, Integer>{
    
    @Query(value = "SELECT new com.example.blog.dto.PublishedContentListDTO(c.contentId, c.title, c.excerpt, c.imageUrl) FROM Content c WHERE c.isPublished = TRUE")
    List<PublishedContentListDTO> findPublishedArticles();

    @Query(value = "SELECT new com.example.blog.dto.AllContentListDTO(c.contentId, c.title,c.excerpt ,c.imageUrl, c.contentUrl, c.updatedAt, c.isPublished) FROM Content c")
    List<AllContentListDTO> findAllArticles();

    @Query(value = "SELECT new com.example.blog.dto.ArticleDTO(c.title, c.imageUrl, c.contentUrl, c.updatedAt) FROM Content c WHERE isPublished = TRUE AND c.contentId = :contentId")
    ArticleDTO findArticleByContentId(@Param("contentId") Integer contentId);
}
