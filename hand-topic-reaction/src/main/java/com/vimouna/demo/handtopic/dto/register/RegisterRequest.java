package com.vimouna.demo.handtopic.dto.register;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class RegisterRequest {
    @NotBlank
    private final String fullname;
    @NotBlank
    private final String email;
    @NotBlank
    private final String password;
}
