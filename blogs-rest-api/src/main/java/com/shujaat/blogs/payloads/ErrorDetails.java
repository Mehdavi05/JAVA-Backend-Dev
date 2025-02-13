package com.shujaat.blogs.payloads;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Date;

public class ErrorDetails {

    @Schema(
            description = "Error details timestamp"
    )
    private Date timestamp;

    @Schema(
            description = "Error details message"
    )
    private String message;

    @Schema(
            description = "Error details"
    )
    private String details;

    public ErrorDetails() {
    }

    public ErrorDetails(Date timestamp, String message, String details) {
        this.timestamp = timestamp;
        this.message = message;
        this.details = details;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
