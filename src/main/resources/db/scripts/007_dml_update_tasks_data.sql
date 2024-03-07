-- liquibase formatted sql
-- changeset liquibase:007
UPDATE tasks SET priority_id = (SELECT id FROM priorities WHERE name = 'urgently');
-- rollback ;
