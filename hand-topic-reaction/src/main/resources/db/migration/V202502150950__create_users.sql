CREATE TABLE users
(
    id        SERIAL                                  NOT NULL,
    full_name VARCHAR(100),
    username  VARCHAR(100)                            NOT NULL,
    password  VARCHAR(120)                            NOT NULL,
    role      VARCHAR(20)                             NOT NULL,
    email     VARCHAR(150)                            NOT NULL,
    created_by          varchar(150) NOT NULL DEFAULT 'system',
    created_date        timestamp DEFAULT CURRENT_TIMESTAMP not null,
    last_modified_by    varchar(150) DEFAULT NULL,
    last_modified_date  timestamp NULL DEFAULT NULL,
    deleted_at          timestamp NULL DEFAULT NULL,
    CONSTRAINT pk_users PRIMARY KEY (id)
);

ALTER TABLE users
    ADD CONSTRAINT uc_users_username UNIQUE (username);

ALTER SEQUENCE users_id_seq RESTART WITH 3;