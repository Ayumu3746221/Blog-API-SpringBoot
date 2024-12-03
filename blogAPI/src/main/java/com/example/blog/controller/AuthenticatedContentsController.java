package com.example.blog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.blog.dto.AllContentListDTO;
import com.example.blog.service.ContentService;


@RestController
@RequestMapping("/api/auth/v1")
public class AuthenticatedContentsController {

    @Autowired
    private ContentService contentService;

    @GetMapping("/authenticated/contents")
    public List<AllContentListDTO> getAllContents() {
        return contentService.getAllArticles();
    }
}