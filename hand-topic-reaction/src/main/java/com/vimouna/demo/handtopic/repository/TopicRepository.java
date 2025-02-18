package com.vimouna.demo.handtopic.repository;

import com.vimouna.demo.handtopic.entities.TopicEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TopicRepository extends JpaRepository<TopicEntity, Long> {
}
