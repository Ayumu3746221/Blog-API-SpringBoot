package com.example.blog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.blog.dto.ArticleDTO;
import com.example.blog.dto.PublishedContentListDTO;
import com.example.blog.service.ContentService;

@RestController
@RequestMapping("/api/public/v1")
public class PublicContentsController {
    
    @Autowired
    private ContentService contentService;

    @GetMapping("/published/articles")
    public List<PublishedContentListDTO> getPublishedArticles() {
        return contentService.getPublishedArticles();
    }

    @GetMapping("/articles/{contentId}")
    public ArticleDTO getArticleByContentId(@PathVariable("contentId") Integer contentId) {
        return contentService.getArticleByContentId(contentId);
    }
}
