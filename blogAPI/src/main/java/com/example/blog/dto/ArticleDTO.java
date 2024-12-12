package com.example.blog.dto;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class ArticleDTO {
    private String title;
    private String excerpt;
    private String imageUrl;
    private String contentUrl;
    private Timestamp updatedAt;

    public ArticleDTO(String title,String excerpt ,String imageUrl, String contentUrl, Timestamp updatedAt) {
        this.title = title;
        this.excerpt = excerpt;
        this.imageUrl = imageUrl;
        this.contentUrl = contentUrl;
        this.updatedAt = updatedAt;
    }
}
