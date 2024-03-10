-- liquibase formatted sql
-- changeset liquibase:010
CREATE TABLE tasks_categories
(
    id          SERIAL PRIMARY KEY,
    task_id     INT REFERENCES tasks (id),
    category_id INT REFERENCES categories (id)
);
-- rollback DROP TABLE tasks_categories;
