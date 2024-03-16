-- liquibase formatted sql
-- changeset liquibase:012
ALTER TABLE tasks ADD COLUMN temp_created TIMESTAMP WITHOUT TIME ZONE DEFAULT NULL;
UPDATE tasks SET temp_created = created::TIMESTAMP;
ALTER TABLE tasks DROP COLUMN created;
ALTER TABLE tasks ADD COLUMN created TIMESTAMP WITHOUT TIME ZONE DEFAULT NULL;
UPDATE tasks SET created = tasks.temp_created;
ALTER TABLE tasks DROP COLUMN temp_created;
-- rollback ;
