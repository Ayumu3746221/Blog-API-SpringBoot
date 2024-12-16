package com.example.blog.dto;

import com.example.blog.entity.Filename;

import lombok.Data;

@Data
public class FilenameDTO {
    
    private Integer contentId;
    private Integer userId;
    private String filename;

    public Filename toEntiry(){
        Filename entity = new Filename();
        entity.setContentId(this.contentId);
        entity.setUserId(this.userId);
        entity.setFilename(this.filename);
        return entity;
    }
}
