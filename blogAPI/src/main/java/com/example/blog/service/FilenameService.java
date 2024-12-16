package com.example.blog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.blog.dto.FilenameDTO;
import com.example.blog.repository.FilenameRepository;

@Service
public class FilenameService {
    
    @Autowired
    private FilenameRepository filenameRepository;

    public String getObjectName(Integer contentId){
        String objectName = filenameRepository.findByContentId(contentId).getFilename();
        return objectName;
    }

    public void saveObjectName(Integer contentId , Integer userId , String filename){
        FilenameDTO filenameDTO = new FilenameDTO();
        filenameDTO.setContentId(contentId);
        filenameDTO.setUserId(userId);
        filenameDTO.setFilename(filename);
        filenameRepository.save(filenameDTO.toEntiry());
    }

    public void deleteObjectName(Integer contentId){
        filenameRepository.deleteByContentId(contentId);
    }
}
