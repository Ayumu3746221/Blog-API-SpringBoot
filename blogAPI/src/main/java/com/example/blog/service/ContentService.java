package com.example.blog.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.blog.dto.AllContentListDTO;
import com.example.blog.dto.ArticleDTO;
import com.example.blog.dto.PublishedContentListDTO;
import com.example.blog.repository.ContentRepository;

@Service
public class ContentService {
    
    @Autowired
    private ContentRepository contentRepository;

    public List<PublishedContentListDTO> getPublishedArticles() {
        return contentRepository.findPublishedArticles();
    }

    public List<AllContentListDTO> getAllArticles() {
        return contentRepository.findAllArticles();
    }

    public ArticleDTO getArticleByContentId(Integer contentId) {
        return contentRepository.findArticleByContentId(contentId);
    }
}
