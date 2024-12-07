package com.example.blog.dto;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class AllContentListDTO {
    
    private Integer contentId;
    private String title;
    private String excerpt;
    private String imageUrl;
    private String contentUrl;
    private Timestamp updatedAt;
    private Boolean isPublished;

    public AllContentListDTO(Integer contentId, String title,String excerpt ,String imageUrl, String contentUrl, Timestamp updatedAt, Boolean isPublished) {
        this.contentId = contentId;
        this.title = title;
        this.excerpt = excerpt;
        this.imageUrl = imageUrl;
        this.contentUrl = contentUrl;
        this.updatedAt = updatedAt;
        this.isPublished = isPublished;
    }
}
