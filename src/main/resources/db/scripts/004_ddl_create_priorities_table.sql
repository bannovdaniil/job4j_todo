-- liquibase formatted sql
-- changeset liquibase:004
CREATE TABLE priorities
(
    id          SERIAL PRIMARY KEY,
    name        TEXT UNIQUE  NOT NULL,
    position    INT,
    description VARCHAR(128) NOT NULL
);
-- rollback DROP TABLE priorities;
