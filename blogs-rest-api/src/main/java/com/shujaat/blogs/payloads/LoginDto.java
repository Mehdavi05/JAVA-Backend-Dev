package com.shujaat.blogs.payloads;

import io.swagger.v3.oas.annotations.media.Schema;

public class LoginDto {
    @Schema(
            description = "Login username or email"
    )
    private String usernameOrEmail;

    @Schema(
            description = "Login username password"
    )
    private String password;

    public LoginDto() {
    }

    public LoginDto(String usernameOrEmail, String password) {
        this.usernameOrEmail = usernameOrEmail;
        this.password = password;
    }

    public String getUsernameOrEmail() {
        return usernameOrEmail;
    }

    public void setUsernameOrEmail(String usernameOrEmail) {
        this.usernameOrEmail = usernameOrEmail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
