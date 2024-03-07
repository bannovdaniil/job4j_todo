-- liquibase formatted sql
-- changeset liquibase:005
ALTER TABLE tasks ADD COLUMN priority_id int REFERENCES priorities(id);
-- rollback ALTER TABLE tasks DROP COLUM priority_id;
