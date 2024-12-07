package com.example.blog.dto;

import lombok.Data;

@Data
public class PublishedContentListDTO {
    private Integer contentId;
    private String title;
    private String excerpt;
    private String imageUrl;

    public PublishedContentListDTO(Integer contentId, String title, String excerpt, String imageUrl) {
        this.contentId = contentId;
        this.title = title;
        this.excerpt = excerpt;
        this.imageUrl = imageUrl;
    }
}
