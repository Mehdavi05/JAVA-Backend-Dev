package com.shujaat.blogs.payloads;

import io.swagger.v3.oas.annotations.media.Schema;

public class RegisterUserDto {
    @Schema(
            description = "Register user name"
    )
    private String name;

    @Schema(
            description = "Register user username"
    )
    private String username;

    @Schema(
            description = "Register user email id"
    )
    private String email;

    @Schema(
            description = "Register user password"
    )
    private String password;

    public RegisterUserDto() {
    }

    public RegisterUserDto(String name, String username, String email, String password) {
        this.name = name;
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
