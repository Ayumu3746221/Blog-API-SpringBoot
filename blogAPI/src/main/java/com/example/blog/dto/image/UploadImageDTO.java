package com.example.blog.dto.image;

import com.example.blog.entity.Images;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UploadImageDTO {
    
    @NotNull
    private Integer userId;

    @NotNull
    private String title;

    @NotNull
    private String imageUrl;

    public Images toEntity() {
        Images image = new Images();
        image.setUserId(userId);
        image.setTitle(title);
        image.setImageUrl(imageUrl);
        return image;
    }
}
