package com.example.blog.service;

import java.util.Date;

import org.springframework.stereotype.Service;

@Service
public class NamingService {
    
    public String generateFileName() {
        Date date = new Date();
        return "article_" + date.getTime() + ".md";
    }
}
