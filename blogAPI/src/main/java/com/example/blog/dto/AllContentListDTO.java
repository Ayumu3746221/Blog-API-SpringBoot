package com.example.blog.dto;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class AllContentListDTO {
    
    private Integer contentId;
    private String title;
    private Timestamp updatedAt;
    private Boolean isPublished;

    public AllContentListDTO(Integer contentId, String title,Timestamp updatedAt, Boolean isPublished) {
        this.contentId = contentId;
        this.title = title;
        this.updatedAt = updatedAt;
        this.isPublished = isPublished;
    }
}
