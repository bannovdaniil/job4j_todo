-- liquibase formatted sql
-- changeset liquibase:011
ALTER TABLE users ADD COLUMN user_zone VARCHAR(128);
-- rollback ALTER TABLE users DROP COLUM user_zone;
