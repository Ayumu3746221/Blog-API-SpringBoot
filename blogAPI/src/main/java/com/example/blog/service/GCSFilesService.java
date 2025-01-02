package com.example.blog.service;

import java.nio.file.Files;
import java.nio.file.Path;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

@Service
public class GCSFilesService {
    
    @Value("${gcs.project-id}")
    private String projectId;

    @Value("${gcs.bucket-name}")
    private String bucketName;

    @Value("${gcs.bucket-url}")
    private String bucketUrl;

    private Storage getStorage() {
        return StorageOptions.newBuilder().setProjectId(projectId).build().getService();
    }

    public String uploadArticleFile(String objectName, Path tmpFilePath) throws Exception {
        Storage storage = getStorage();
        String fullObjectName = "articles/" + objectName;
        BlobId blobId = BlobId.of(bucketName, fullObjectName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType("text/markdown").build();
       
        byte[] fileBytes = Files.readAllBytes(tmpFilePath);
        storage.create(blobInfo, fileBytes);

        String url = bucketUrl + fullObjectName;

        return  url;
    }

    public void deleteArticleFile(String objectName) throws Exception {
        Storage storage = getStorage();
        String fullObjectName = "articles/" + objectName;
        BlobId blobId = BlobId.of(bucketName, fullObjectName);
        storage.delete(blobId);
    }

    public String uploadImageFile(String objectName , MultipartFile file) throws Exception {
        Storage storage = getStorage();
        String fullObjectName = "images/" + objectName;
        BlobId blobId = BlobId.of(bucketName, fullObjectName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType(file.getContentType()).build();

        byte[] fileBytes = file.getBytes();
        storage.create(blobInfo, fileBytes);

        String url = bucketUrl + fullObjectName;

        return url;
    }

    public void deleteImageFile(String objectName) throws Exception {
        Storage storage = getStorage();
        String fullObjectName = "images/" + objectName;
        BlobId boloId = BlobId.of(bucketName, fullObjectName);
        
        Blob blob = storage.get(boloId);
        if (blob == null) {
            throw new IllegalArgumentException("Object not found:" + fullObjectName);
        }

        boolean deleted = storage.delete(boloId);
        if (!deleted) {
            throw new RuntimeException("Error deleting object:" + fullObjectName);
        }
    }
}
