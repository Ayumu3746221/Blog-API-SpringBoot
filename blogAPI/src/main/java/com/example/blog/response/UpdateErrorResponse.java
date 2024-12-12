package com.example.blog.response;

import lombok.Getter;

@Getter
public class UpdateErrorResponse {
    private final String message;
    private final String details;

    public UpdateErrorResponse(String message, String details) {
        this.message = message;
        this.details = details;
    }
}
