package com.example.blog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.blog.repository.FilenameRepository;

@Service
public class FilenameService {
    
    @Autowired
    private FilenameRepository filenameRepository;

    public String getObjectName(Integer contentId){
        String objectName = filenameRepository.findByContentId(contentId).getFilename();
        return objectName;
    }
}
