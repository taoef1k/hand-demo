package com.vimouna.demo.handtopic.repository;

import com.vimouna.demo.handtopic.entities.TopicEntity;
import com.vimouna.demo.handtopic.entities.UserEntity;
import com.vimouna.demo.handtopic.entities.UserTopicEntity;
import com.vimouna.demo.handtopic.entities.UserTopicId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface UserTopicRepository extends JpaRepository<UserTopicEntity, UserTopicId> {
    Long countByTopicAndLiked(TopicEntity topic, boolean liked);

    Long countByTopicAndDisliked(TopicEntity topic, boolean disliked);

    List<UserTopicEntity> findAllByUserAndLiked(UserEntity user, boolean liked);

    List<UserTopicEntity> findAllByUserAndDisliked(UserEntity user, boolean disliked);

    Long countByUserTopicId(UserTopicId userTopicId);

    Long countByUserTopicIdAndLiked(UserTopicId userTopicId, boolean liked);

    Long countByUserTopicIdAndDisliked(UserTopicId userTopicId, boolean disliked);

    long countByCreatedDateIsBetweenAndCreatedBy(LocalDateTime startTime, LocalDateTime endTime, String createdBy);
}
