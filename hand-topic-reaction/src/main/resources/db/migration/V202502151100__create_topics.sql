CREATE TABLE topics
(
    id        SERIAL                                  NOT NULL,
    title       VARCHAR(150)                            NOT NULL,
    description TEXT,
    created_by          varchar(150) NOT NULL DEFAULT 'system',
    created_date        timestamp DEFAULT CURRENT_TIMESTAMP not null,
    last_modified_by    varchar(150) DEFAULT NULL,
    last_modified_date  timestamp NULL DEFAULT NULL,
    deleted_at          timestamp NULL DEFAULT NULL,
    CONSTRAINT pk_topics PRIMARY KEY (id)
);

ALTER SEQUENCE topics_id_seq RESTART WITH 5;