package com.eyecare.application.service.authentication;

import com.eyecare.application.dao.auth.Login;

public interface AuthService {
    String login(Login loginDto);
}
