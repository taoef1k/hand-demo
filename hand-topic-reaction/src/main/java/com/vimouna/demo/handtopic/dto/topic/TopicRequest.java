package com.vimouna.demo.handtopic.dto.topic;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class TopicRequest {
    @NotBlank
    private final String title;
    @NotBlank
    private final String description;
}
