package com.example.blog.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "content_user_failes")
@Data
public class Filename {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    @JsonProperty("id")
    private Integer id;

    @Column(name="content_id")
    @JsonProperty("contentId")
    private Integer contentId;

    @Column(name="user_id")
    @JsonProperty("userId")
    private Integer userId;

    @Column(name="filename")
    @JsonProperty("filename")
    private String filename;
}
