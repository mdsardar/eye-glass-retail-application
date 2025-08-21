package com.eyecare.application.dao.auth.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JwtAuthResponse {
    private String accessToken;
    private String tokenType = "Bearer";
}
