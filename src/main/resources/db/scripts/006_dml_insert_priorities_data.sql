-- liquibase formatted sql
-- changeset liquibase:006
INSERT INTO priorities (name, position, description) VALUES ('urgently', 1, 'Срочный');
INSERT INTO priorities (name, position, description) VALUES ('normal', 2, 'Нормальный');
-- rollback DELETE FROM priorities;
