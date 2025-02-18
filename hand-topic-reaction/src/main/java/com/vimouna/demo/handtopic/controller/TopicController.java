package com.vimouna.demo.handtopic.controller;

import com.vimouna.demo.handtopic.dto.topic.LikedDislikedResponse;
import com.vimouna.demo.handtopic.dto.topic.TopicRequest;
import com.vimouna.demo.handtopic.dto.topic.TopicResponse;
import com.vimouna.demo.handtopic.services.TopicService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(
        value = "/topics",
        produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class TopicController {

    private final TopicService topicService;

    @GetMapping
    public ResponseEntity<List<TopicResponse>> index() {
        return ResponseEntity.ok(topicService.findAll());
    }

    @PostMapping
    public ResponseEntity<TopicResponse> addNewTopic(@RequestBody @Valid TopicRequest request) {
        return ResponseEntity.ok(topicService.addNewTopic(request));
    }

    @PostMapping("/liked/{topicId}")
    public ResponseEntity<LikedDislikedResponse> liked(@PathVariable("topicId") Long topicId) {
        return ResponseEntity.ok(topicService.topicLiked(topicId));
    }

    @PostMapping("/disliked/{topicId}")
    public ResponseEntity<LikedDislikedResponse> disliked(@PathVariable("topicId") Long topicId) {
        return ResponseEntity.ok(topicService.topicDisliked(topicId));
    }
}
