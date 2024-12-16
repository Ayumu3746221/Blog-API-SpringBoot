package com.example.blog.dto;

import java.sql.Timestamp;

import com.example.blog.entity.Content;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ContentDTO {

    @NotNull
    private Integer userId;

    @NotEmpty
    @Size(max = 100)
    private String title;

    @NotNull
    private String excerpt;

    @NotNull
    private String imageUrl;

    @NotEmpty
    private String content;

    public Content toEntity(String contentUrl) {
        Content newContent = new Content();

        newContent.setUserId(this.userId);
        newContent.setTitle(this.title);
        newContent.setExcerpt(this.excerpt);
        newContent.setImageUrl(this.imageUrl);
        newContent.setContentUrl(contentUrl);
        newContent.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        newContent.setIsPublished(true);
        return newContent;
    }
}
