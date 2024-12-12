package com.example.blog.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.blog.dto.AllContentListDTO;
import com.example.blog.dto.ArticleDTO;
import com.example.blog.dto.PublishedContentListDTO;
import com.example.blog.dto.UpdateArticleDTO;
import com.example.blog.entity.Content;
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

    public void updateArticle(UpdateArticleDTO updateArticleDTO) {

        Content oldContent = contentRepository.findByContentId(updateArticleDTO.getContentId());
        Content newContent = updateArticleDTO.updateEntity(oldContent, updateArticleDTO.getTitle(), updateArticleDTO.getExcerpt(), updateArticleDTO.getImageUrl(), updateArticleDTO.getIsPublished());

        contentRepository.save(newContent);
    }
}
