-- liquibase formatted sql
-- changeset liquibase:003
ALTER TABLE tasks
    ADD COLUMN user_id INT NOT NULL REFERENCES users (id);
-- rollback ALTER TABLE tasks DROP COLUM user_id;
