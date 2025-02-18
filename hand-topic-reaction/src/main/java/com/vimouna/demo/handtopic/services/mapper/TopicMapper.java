package com.vimouna.demo.handtopic.services.mapper;

import com.vimouna.demo.handtopic.dto.topic.TopicRequest;
import com.vimouna.demo.handtopic.dto.topic.TopicResponse;
import com.vimouna.demo.handtopic.entities.TopicEntity;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.FIELD)
public interface TopicMapper {
    @Mappings({
            @Mapping(target = "topicId", source = "topic.id"),
            @Mapping(target = "likes", source = "totalLiked"),
            @Mapping(target = "dislikes", source = "totalDisliked"),
            @Mapping(target = "liked", source = "liked"),
            @Mapping(target = "disliked", source = "disliked")

    })
    TopicResponse toTopicDto(TopicEntity topic, String author, Long totalLiked, Long totalDisliked, boolean liked, boolean disliked);

    TopicEntity toTopicEntity(TopicRequest topicRequest);

    TopicResponse toTopicResponse(TopicEntity topic);
}
