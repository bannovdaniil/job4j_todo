-- liquibase formatted sql
-- changeset liquibase:002
CREATE TABLE users
(
    id       SERIAL PRIMARY KEY,
    name     VARCHAR(128) NOT NULL,
    login    VARCHAR(20)  NOT NULL UNIQUE,
    password VARCHAR(20)  NOT NULL
);
-- rollback DROP TABLE users;
