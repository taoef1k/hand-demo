package com.vimouna.demo.handtopic.dto.login;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Date;

@RequiredArgsConstructor
@Getter
public class LoginResponse {
    private final String accessToken;
    private final Date createdAt;
    private final Date expiresAt;
}