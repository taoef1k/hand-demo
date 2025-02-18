package com.vimouna.demo.handtopic.dto.register;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class RegisterResponse {
    private final String email;
}
