package com.shujaat.blogs.exceptions;

import org.springframework.http.HttpStatus;

public class BlogAPIException extends RuntimeException {
    private HttpStatus status;
    private String message;

    public BlogAPIException(HttpStatus status, String message) {
        super(String.format("Request failed with status: %s and message: '%s'",status, message));
        this.status = status;
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
