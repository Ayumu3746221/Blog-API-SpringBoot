package com.example.blog.service;

import java.nio.file.Files;
import java.nio.file.Path;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

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

    private Storage getStorage() {
        return StorageOptions.newBuilder().setProjectId(projectId).build().getService();
    }

    public String uploadArticleFile(String objectName, Path tmpFilePath) throws Exception {
        Storage storage = getStorage();
        String fullObjectName = "articles/" + objectName;
        BlobId blobId = BlobId.of(bucketName, fullObjectName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType("text/markdown").build();
       
        byte[] fileBytes = Files.readAllBytes(tmpFilePath);
        Blob response =  storage.create(blobInfo, fileBytes);

        return  response.getMediaLink();
    }

    public void deleteArticleFile(String objectName) throws Exception {
        Storage storage = getStorage();
        String fullObjectName = "articles/" + objectName;
        BlobId blobId = BlobId.of(bucketName, fullObjectName);
        storage.delete(blobId);
    }
}
