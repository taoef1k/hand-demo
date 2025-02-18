package com.vimouna.demo.handtopic.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Setter
@Getter
public class UserTopicId implements Serializable {
    @Column
    private Long topicId;
    @Column
    private Long userId;
}
