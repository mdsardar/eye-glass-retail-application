package com.eyecare.application.dao.auth;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Login {
    private String usernameOrEmail;
    private String password;
}