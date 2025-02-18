package com.vimouna.demo.handtopic.dto.topic;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class LikedDislikedResponse {
    private Long likes;
    private Long dislikes;
    private boolean liked;
    private boolean disliked;
}
