package com.example.blog.dto;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class PublishedContentListDTO {
    private Integer contentId;
    private String title;
    private String imageUrl;
    private String contentUrl;
    private Timestamp updatedAt;

    public PublishedContentListDTO(Integer contentId, String title, String imageUrl, String contentUrl, Timestamp updatedAt) {
        this.contentId = contentId;
        this.title = title;
        this.imageUrl = imageUrl;
        this.contentUrl = contentUrl;
        this.updatedAt = updatedAt;
    }
}
