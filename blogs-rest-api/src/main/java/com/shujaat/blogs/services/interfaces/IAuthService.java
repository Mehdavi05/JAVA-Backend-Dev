package com.shujaat.blogs.services.interfaces;

import com.shujaat.blogs.payloads.LoginDto;

public interface IAuthService {
    String login(LoginDto loginDto);
}
