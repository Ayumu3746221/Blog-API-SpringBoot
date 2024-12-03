package com.example.blog.entity;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "contents")
@Data
public class Content {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="content_id")
    @JsonProperty("contentId")
    private Integer contentId;

    @Column(name="user_id")
    @JsonProperty("userId")
    private Integer userId;

    @Column(name="title")
    @JsonProperty("title")
    private String title;

    @Column(name="image_url")
    @JsonProperty("imageUrl")
    private String imageUrl;

    @Column(name="content_url")
    @JsonProperty("contentUrl")
    private String contentUrl;

    @Column(name="updated_at")
    @JsonProperty("updatedAt")
    private Timestamp updatedAt;

    @Column(name="is_published")
    @JsonProperty("isPublished")
    private Boolean isPublished;

}
