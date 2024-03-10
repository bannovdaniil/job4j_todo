-- liquibase formatted sql
-- changeset liquibase:008
CREATE TABLE categories
(
    id          SERIAL PRIMARY KEY,
    name        TEXT UNIQUE  NOT NULL
);
-- rollback DROP TABLE categories;
