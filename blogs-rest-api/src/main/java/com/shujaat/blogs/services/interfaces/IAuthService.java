package com.shujaat.blogs.services.interfaces;

import com.shujaat.blogs.payloads.LoginDto;
import com.shujaat.blogs.payloads.RegisterUserDto;

public interface IAuthService {
    String login(LoginDto loginDto);
    String register(RegisterUserDto registerUserDto);
}
