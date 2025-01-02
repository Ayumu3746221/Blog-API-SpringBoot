package com.example.blog.dto.image;

import lombok.Data;

@Data
public class ImageListDTO {
    
    private Integer id;
    private String title;
    private String imageUrl;

    public ImageListDTO(Integer id, String title, String imageUrl) {
        this.id = id;
        this.title = title;
        this.imageUrl = imageUrl;
    }
}
