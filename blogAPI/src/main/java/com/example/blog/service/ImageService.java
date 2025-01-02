package com.example.blog.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.blog.dto.image.ImageListDTO;
import com.example.blog.dto.image.UploadImageDTO;
import com.example.blog.repository.ImagesRepository;

@Service
public class ImageService {
    
    @Autowired
    private ImagesRepository imagesRepository;

    public List<ImageListDTO> getImageList() {
        return imagesRepository.findAllImages();
    }

    public void saveImage( UploadImageDTO uploadImageDTO) {
        imagesRepository.save(uploadImageDTO.toEntity());
    }

    public void  deleteImage(Integer id) {
        imagesRepository.deleteById(id);
    }
}
