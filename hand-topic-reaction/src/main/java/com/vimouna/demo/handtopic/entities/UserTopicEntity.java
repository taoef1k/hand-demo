package com.vimouna.demo.handtopic.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users_topic")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserTopicEntity extends Auditable<String> {

    @EmbeddedId
    private UserTopicId userTopicId;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("topicId")
    private TopicEntity topic;

    private Boolean liked = false;

    private Boolean disliked = false;

}
