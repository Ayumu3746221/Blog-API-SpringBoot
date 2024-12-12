package com.example.blog.service;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class GCSFilesService {
    
    @Value("${gcs.bucket.name}")
    private String articlesBucketName;

    private final RestTemplate restTemplate;

    public GCSFilesService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void uploadArticleFile(String objectName, Path tmpFilePath) throws Exception {
        String url = String.format("https://storage.googleapis.com/upload/storage/v1/b/%s/o", articlesBucketName);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("text", "markdown" , StandardCharsets.UTF_8));
        
        byte[] fileToBytes = Files.readAllBytes(tmpFilePath);
        HttpEntity<byte[]> requestEntity = new HttpEntity<>(fileToBytes, headers);
        
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url)
            .queryParam("uploadType", "media")
            .queryParam("name", "articles/" + objectName);

        ResponseEntity<String> response = restTemplate.exchange(
            builder.toUriString(),
            HttpMethod.POST,
            requestEntity,
            String.class
        );

        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new Exception("Failed to upload file to GCS: " + response.getBody());
        }

        System.out.println("File uploaded successfully to GCS");
    }
}
