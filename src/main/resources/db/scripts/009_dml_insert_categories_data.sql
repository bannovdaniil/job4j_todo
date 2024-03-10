-- liquibase formatted sql
-- changeset liquibase:006
INSERT INTO categories (name) VALUES ('Домашние');
INSERT INTO categories (name) VALUES ('Рабочие');
INSERT INTO categories (name) VALUES ('Не срочные');
INSERT INTO categories (name) VALUES ('Интересное');
-- rollback DELETE FROM categories;
