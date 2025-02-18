package com.vimouna.demo.handtopic.services;

import com.vimouna.demo.handtopic.dto.topic.LikedDislikedResponse;
import com.vimouna.demo.handtopic.dto.topic.TopicRequest;
import com.vimouna.demo.handtopic.dto.topic.TopicResponse;
import com.vimouna.demo.handtopic.entities.TopicEntity;
import com.vimouna.demo.handtopic.entities.UserEntity;
import com.vimouna.demo.handtopic.entities.UserTopicEntity;
import com.vimouna.demo.handtopic.entities.UserTopicId;
import com.vimouna.demo.handtopic.exceptions.ServiceException;
import com.vimouna.demo.handtopic.repository.TopicRepository;
import com.vimouna.demo.handtopic.repository.UserRepository;
import com.vimouna.demo.handtopic.repository.UserTopicRepository;
import com.vimouna.demo.handtopic.services.mapper.TopicMapper;
import com.vimouna.demo.handtopic.utils.AppUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class TopicService {
    private final TopicRepository topicRepository;
    private final TopicMapper topicMapper;
    private final UserTopicRepository userTopicRepository;
    private final UserRepository userRepository;

    public List<TopicResponse> findAll() {
        try {
            List<TopicEntity> topics = topicRepository.findAll();
            UserEntity userEntity = userRepository.findByEmail(AppUtil.getPrincipalUsername()).orElse(new UserEntity());
            List<UserTopicEntity> userTopicLikes = userTopicRepository.findAllByUserAndLiked(userEntity, true);
            List<UserTopicEntity> userTopicDislikes = userTopicRepository.findAllByUserAndDisliked(userEntity, true);

            return topics.stream()
                    .map(topic -> this.toTopicDto(topic, userTopicLikes, userTopicDislikes))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("::ERROR::", e);
            throw new ServiceException("Invalid Find all topic process");
        }
    }

    @Transactional
    public LikedDislikedResponse topicLiked(Long topicId) {
        return topicLikedDisliked(topicId, true);
    }

    @Transactional
    public LikedDislikedResponse topicDisliked(Long topicId) {
        return topicLikedDisliked(topicId, false);
    }

    @Transactional
    public TopicResponse addNewTopic(TopicRequest request) {
        try {
            TopicEntity topic = topicRepository.save(topicMapper.toTopicEntity(request));
            return topicMapper.toTopicResponse(topic);
        } catch (ServiceException e) {
            log.error("::: Service Exception when add new topic process :::", e);
            throw new ServiceException(e.getMessage());
        } catch (Exception e) {
            log.error("::ERROR::", e);
            throw new ServiceException("Invalid add new topic process");
        }
    }

    private LikedDislikedResponse topicLikedDisliked(Long topicId, boolean status) {
        try {
            long countRecords = userTopicRepository.countByCreatedDateIsBetweenAndCreatedBy(
                    LocalDate.now().atTime(0, 10),
                    LocalDate.now().atTime(23, 59),
                    AppUtil.getPrincipalUsername()
            );

            if (countRecords > 4) {
                throw new ServiceException("You have reached the maximum number of rates for today.");
            }

            TopicEntity topic = topicRepository.findById(topicId).orElseThrow(() -> new UsernameNotFoundException("Topic ID not found"));
            UserEntity user = userRepository.findByEmail(AppUtil.getPrincipalUsername()).orElseThrow(() -> new UsernameNotFoundException("User ID not found"));

            UserTopicId userTopicId = new UserTopicId();
            userTopicId.setTopicId(topicId);
            userTopicId.setUserId(user.getId());

            long countTopicAndStatus = 0;
            if (status)
                countTopicAndStatus = userTopicRepository.countByUserTopicIdAndDisliked(userTopicId, true);
            else
                countTopicAndStatus = userTopicRepository.countByUserTopicIdAndLiked(userTopicId, true);

            if (countTopicAndStatus > 0) {
                deleteUserTopic(user, topic);
                saveUserTopic(user, topic, status);
            } else if (userTopicRepository.countByUserTopicId(userTopicId) > 0) {
                deleteUserTopic(user, topic);
            } else {
                saveUserTopic(user, topic, status);
            }

            List<UserTopicEntity> userTopicLikes = userTopicRepository.findAllByUserAndLiked(user, true);
            List<UserTopicEntity> userTopicDislikes = userTopicRepository.findAllByUserAndDisliked(user, true);

            return LikedDislikedResponse.builder()
                    .likes(userTopicRepository.countByTopicAndLiked(topic, true))
                    .dislikes(userTopicRepository.countByTopicAndDisliked(topic, true))
                    .liked(userLiked(topic, userTopicLikes))
                    .disliked(userDisliked(topic, userTopicDislikes))
                    .build();
        } catch (ServiceException e) {
            log.error("::: Service Exception when voted process :::", e);
            throw new ServiceException(e.getMessage());
        } catch (Exception e) {
            log.error("::ERROR::", e);
            throw new ServiceException("Invalid voted process");
        }
    }

    private TopicResponse toTopicDto(TopicEntity topic, List<UserTopicEntity> userTopicLikes, List<UserTopicEntity> userTopicDislikes) {
        boolean liked = userLiked(topic, userTopicLikes);
        boolean disliked = userDisliked(topic, userTopicDislikes);

        UserEntity userEntity = userRepository.findByEmail(topic.getCreatedBy()).orElse(new UserEntity());

        return topicMapper.toTopicDto(topic,
                userEntity.getFullName(),
                userTopicRepository.countByTopicAndLiked(topic, true),
                userTopicRepository.countByTopicAndDisliked(topic, true),
                liked, disliked);
    }

    private void saveUserTopic(UserEntity user, TopicEntity topic, boolean liked) {
        UserTopicId userTopicId = new UserTopicId();
        userTopicId.setTopicId(topic.getId());
        userTopicId.setUserId(user.getId());

        UserTopicEntity userTopic = new UserTopicEntity();
        userTopic.setUserTopicId(userTopicId);
        userTopic.setTopic(topic);
        userTopic.setUser(user);
        if (liked) {
            userTopic.setLiked(true);
            userTopic.setDisliked(false);
        } else {
            userTopic.setLiked(false);
            userTopic.setDisliked(true);
        }
        userTopicRepository.save(userTopic);
    }

    private void deleteUserTopic(UserEntity user, TopicEntity topic) {
        UserTopicId userTopicId = new UserTopicId();
        userTopicId.setTopicId(topic.getId());
        userTopicId.setUserId(user.getId());

        userTopicRepository.deleteById(userTopicId);
    }

    private boolean userLiked(TopicEntity topic, List<UserTopicEntity> userTopicLikes) {
        if (!userTopicLikes.isEmpty()) {
            return userTopicLikes.stream()
                    .anyMatch(userTopicEntity -> userTopicEntity.getTopic().getId().equals(topic.getId()));
        }
        return false;
    }

    private boolean userDisliked(TopicEntity topic, List<UserTopicEntity> userTopicDislikes) {
        if (!userTopicDislikes.isEmpty()) {
            return userTopicDislikes.stream()
                    .anyMatch(userTopicEntity -> userTopicEntity.getTopic().getId().equals(topic.getId()));
        }
        return false;
    }
}
