package com.example.blog.dto;

import java.sql.Timestamp;

import com.example.blog.entity.Content;

import lombok.Data;

@Data
public class UpdateArticleDTO {
    private Integer contentId;
    private String title;
    private String excerpt;
    private String imageUrl;
    private String content;
    private Boolean isPublished;

    public Content updateEntity(Content oldContent, String title, String excerpt, String imageUrl, Boolean isPublished){

        Content newContent = new Content();
        newContent.setContentId(oldContent.getContentId());
        newContent.setUserId(oldContent.getUserId());
        newContent.setTitle(title);
        newContent.setExcerpt(excerpt);
        newContent.setImageUrl(imageUrl);
        newContent.setContentUrl(oldContent.getContentUrl());
        newContent.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        newContent.setIsPublished(isPublished);

        return newContent;
    }
}
