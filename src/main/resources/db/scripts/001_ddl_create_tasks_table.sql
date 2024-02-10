-- liquibase formatted sql
-- changeset liquibase:001
CREATE TABLE tasks
(
    id          SERIAL PRIMARY KEY,
    description VARCHAR,
    created     TIMESTAMP,
    done        BOOLEAN DEFAULT FALSE
);
-- rollback DROP TABLE tasks;
