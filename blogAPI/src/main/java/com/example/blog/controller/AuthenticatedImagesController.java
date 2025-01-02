package com.example.blog.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.blog.dto.image.DeleteGcsImageDTO;
import com.example.blog.dto.image.DeleteImageDTO;
import com.example.blog.dto.image.ImageListDTO;
import com.example.blog.dto.image.UploadImageDTO;
import com.example.blog.response.ErrorResponse;
import com.example.blog.response.SuccessResponse;
import com.example.blog.response.UploadSuccessResponse;
import com.example.blog.service.GCSFilesService;
import com.example.blog.service.ImageService;


@RestController
@RequestMapping("/api/auth/v1")
public class AuthenticatedImagesController {

    @Autowired
    private ImageService imageService;

    @Autowired
    private GCSFilesService gcsFilesService;
    
    @GetMapping("/authencated/get/images")
    public List<ImageListDTO> getImageList() {
        return imageService.getImageList();
    }

    @PostMapping("/authencated/upload/image")
    public ResponseEntity<?> postImage(@RequestBody @Validated UploadImageDTO request){

        try {
            imageService.saveImage(request);
            return ResponseEntity.ok(new SuccessResponse("Image uploaded to database successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse("Failed to upload image to database", e.getMessage()));
        }
    }

    @PostMapping("/authencated/upload/gcs/image")
    public ResponseEntity<?> postImageToStorage(@RequestParam("file") MultipartFile file, @RequestParam("objectName") String objectName) {

        long maxFileSize = 5 * 1024 * 1024;

        if (file.getSize() > maxFileSize) {
            return ResponseEntity.badRequest().body(new ErrorResponse("File size too large", "Max file size is 5MB"));
        }

        List<String> allowedTypes = Arrays.asList(
            "image/jpeg",
            "image/png",
            "image/gif"
        );

        if (!allowedTypes.contains(file.getContentType())) {
            return ResponseEntity.badRequest().body(new ErrorResponse("Invalid file type", "Only jpeg, png and gif files are allowed"));
        }
        

        try {
            String imageUrl = gcsFilesService.uploadImageFile(objectName, file);
            return ResponseEntity.ok(new UploadSuccessResponse("Image uploaded to GCS successfully", imageUrl));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse("Failed to upload image to GCS", e.getMessage()));
        }
    }
    
    
    @DeleteMapping("/authencated/delete/image")
    public ResponseEntity<?> deleteImage(@RequestBody DeleteImageDTO request) {

        try {
            imageService.deleteImage(request.getId());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse("When Deleting imageData for database , Error", e.getMessage()));
        }

        return ResponseEntity.ok(new SuccessResponse("Image deleted for database successfully"));
    }

    @DeleteMapping("/authencated/delete/gcs/image")
    public ResponseEntity<?> deleteGCSImage(@RequestBody DeleteGcsImageDTO request) throws Exception {

        try {
            gcsFilesService.deleteImageFile(request.getObjectName());
        }catch(IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("Image not found in GCS", e.getMessage()));
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse("Failed to delete image from GCS", e.getMessage()));
        }

        return ResponseEntity.ok(new SuccessResponse("Image deleted for GCS successfully"));
    }
}
