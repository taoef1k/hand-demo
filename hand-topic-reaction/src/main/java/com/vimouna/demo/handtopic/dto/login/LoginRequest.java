package com.vimouna.demo.handtopic.dto.login;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class LoginRequest {
    @NotBlank
    private final String username;
    @NotBlank
    private final String password;
}
