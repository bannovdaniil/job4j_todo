-- liquibase formatted sql
-- changeset liquibase:001
CREATE TABLE tasks
(
    id          SERIAL PRIMARY KEY,
    title       VARCHAR(128) UNIQUE       NOT NULL,
    description VARCHAR,
    created     TIMESTAMP,
    done        VARCHAR(20) DEFAULT 'NEW' NOT NULL
);
-- rollback DROP TABLE tasks;
