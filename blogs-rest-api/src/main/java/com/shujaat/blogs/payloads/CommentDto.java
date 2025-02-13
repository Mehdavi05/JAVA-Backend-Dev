package com.shujaat.blogs.payloads;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class CommentDto {
    private long id;

    @Schema(
            description = "Comment passer name"
    )
    @NotEmpty(message = "Name should not be null or empty")
    private String name;

    @Schema(
            description = "Comment email"
    )
   @NotEmpty(message = "Email should not be null or empty")
   @Email
    private String email;

    @Schema(
            description = "Comment body"
    )
   @NotEmpty(message = "Body should not be null or empty")
   @Size(min = 10, message = "Body should be at-least 10 characters long")
    private String body;

    public CommentDto() {
    }

    public CommentDto(long id, String name, String email, String body) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.body = body;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
