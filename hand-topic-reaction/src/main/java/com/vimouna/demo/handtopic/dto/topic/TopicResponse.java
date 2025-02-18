package com.vimouna.demo.handtopic.dto.topic;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class TopicResponse {
    private Long topicId;
    private String title;
    private String description;
    private Long likes;
    private Long dislikes;
    private String author;
    private boolean liked;
    private boolean disliked;
}
