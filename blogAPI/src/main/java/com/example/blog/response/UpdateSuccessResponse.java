package com.example.blog.response;

public class UpdateSuccessResponse {
    private final String message;

    public UpdateSuccessResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
