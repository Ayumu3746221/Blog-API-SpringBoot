package com.example.blog.controller;

import java.nio.file.Path;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.blog.dto.AllContentListDTO;
import com.example.blog.dto.ContentDTO;
import com.example.blog.dto.UpdateArticleDTO;
import com.example.blog.entity.Content;
import com.example.blog.response.ErrorResponse;
import com.example.blog.response.SuccessResponse;
import com.example.blog.service.ContentService;
import com.example.blog.service.FileService;
import com.example.blog.service.FilenameService;
import com.example.blog.service.GCSFilesService;
import com.example.blog.service.NamingService;

import jakarta.transaction.Transactional;



@RestController
@RequestMapping("/api/auth/v1")
public class AuthenticatedContentsController {

    @Autowired
    private ContentService contentService;

    @Autowired
    private FilenameService filenameService;

    @Autowired
    private NamingService namingService;

    @Autowired
    private GCSFilesService gcsFilesService;

    @Autowired
    private FileService fileService;

    @GetMapping("/authenticated/contents")
    public List<AllContentListDTO> getAllContents() {
        return contentService.getAllArticles();
    }

    @PutMapping("/authenticated/update/content")
    public ResponseEntity<?> updateContent(@RequestBody UpdateArticleDTO updateArticleDTO) {
        
        String objectName = filenameService.getObjectName(updateArticleDTO.getContentId());
        
        try {
            Path tmpFilePath = fileService.createMarkdownFile(objectName, updateArticleDTO.getContent());          
            gcsFilesService.uploadArticleFile(objectName, tmpFilePath);
            fileService.deleteFile(tmpFilePath);

            contentService.updateArticle(updateArticleDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse("File upload failed" , e.getMessage()));
        }
        
        return ResponseEntity.ok(new SuccessResponse("File uploaded successfully:" + objectName));
    }
    
    @PostMapping("/authenticated/create/content")
    public ResponseEntity<?> postContent(@RequestBody @Validated ContentDTO contentDTO) {
       
        String objectName = namingService.generateFileName();

        try {
            Path tmpFilePath = fileService.createMarkdownFile(objectName, contentDTO.getContent());
            String contentUrl = gcsFilesService.uploadArticleFile(objectName, tmpFilePath);
            fileService.deleteFile(tmpFilePath);

            Content savedContent = contentService.createArticle(contentDTO, contentUrl);
            filenameService.saveObjectName(savedContent.getContentId(), savedContent.getUserId(), objectName);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse("File upload failed" , e.getMessage()));
        }
        
        return ResponseEntity.ok(new SuccessResponse("File uploaded successfully:" + objectName));
    }
    
    @DeleteMapping("/authenticated/delete/{contentId}")
    @Transactional
    public ResponseEntity<?> deleteContent(@PathVariable("contentId") Integer contentId) {
        
        String objeString = filenameService.getObjectName(contentId);

      try {
        gcsFilesService.deleteArticleFile(objeString);
        filenameService.deleteObjectName(contentId);
        contentService.deleteArticle(contentId);
      } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse("File delete failed" , e.getMessage()));
      }

      return ResponseEntity.ok(new SuccessResponse("File deleted successfully:" + objeString));
    }
}