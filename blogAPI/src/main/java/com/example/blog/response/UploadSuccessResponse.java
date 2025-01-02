package com.example.blog.response;

public class UploadSuccessResponse {
    private final String message;
    private final String contentUrl;

    public UploadSuccessResponse(String message , String contentUrl) {
        this.message = message;
        this.contentUrl = contentUrl;
    }

    public String getMessage() {
        return message;
    }

    public String getContentUrl() {
        return contentUrl;
    }
}
