CREATE TABLE users_topic
(
    created_by          varchar(150)        NOT NULL DEFAULT 'system',
    created_date        timestamp DEFAULT CURRENT_TIMESTAMP not null,
    last_modified_by    varchar(150) DEFAULT    NULL,
    last_modified_date  timestamp NULL DEFAULT  NULL,
    deleted_at          timestamp NULL DEFAULT  NULL,
    liked               BOOLEAN                 NULL,
    disliked            BOOLEAN                 NULL,
    topic_id            BIGINT              NOT NULL,
    user_id             BIGINT              NOT NULL,
    CONSTRAINT pk_users_topic PRIMARY KEY (topic_id, user_id)
);

ALTER TABLE users_topic
    ADD CONSTRAINT FK_USERS_TOPIC_ON_TOPIC FOREIGN KEY (topic_id) REFERENCES topics (id);

ALTER TABLE users_topic
    ADD CONSTRAINT FK_USERS_TOPIC_ON_USER FOREIGN KEY (user_id) REFERENCES users (id);