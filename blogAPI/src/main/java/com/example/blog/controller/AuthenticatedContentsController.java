package com.example.blog.controller;

import java.nio.file.Path;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.blog.dto.AllContentListDTO;
import com.example.blog.dto.UpdateArticleDTO;
import com.example.blog.service.ContentService;
import com.example.blog.service.FileService;
import com.example.blog.service.FilenameService;
import com.example.blog.service.GCSFilesService;



@RestController
@RequestMapping("/api/auth/v1")
public class AuthenticatedContentsController {

    @Autowired
    private ContentService contentService;

    @Autowired
    private FilenameService filenameService;

    @Autowired
    private GCSFilesService gcsFilesService;

    @Autowired
    private FileService fileService;

    @GetMapping("/authenticated/contents")
    public List<AllContentListDTO> getAllContents() {
        return contentService.getAllArticles();
    }

    @PostMapping("/authenticated/update/content")
    public String postMethodName(@RequestBody UpdateArticleDTO updateArticleDTO) {

        String objectName = filenameService.getObjectName(updateArticleDTO.getContentId());
        
        try {
            Path tmpFilePath = fileService.createMarkdownFile(objectName, updateArticleDTO.getContent());
            gcsFilesService.uploadArticleFile(objectName, tmpFilePath);
            fileService.deleteFile(tmpFilePath);

            contentService.updateArticle(updateArticleDTO);
        } catch (Exception e) {
            return "Failed to upload file" + e.getMessage();
        }
        
        return "File uploaded successfully";
    }
    
}