package ru.job4j.repository.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.model.Task;
import ru.job4j.repository.TaskRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class TaskRepositoryImpl implements TaskRepository {
    private final CrudRepository crudRepository;

    @Override
    public Task save(Task task) {
        crudRepository.run(session -> session.persist(task));
        return task;
    }

    @Override
    public void update(Task task) {
        crudRepository.run(session -> session.merge(task));
    }

    /**
     * Удалить задачу по id
     *
     * @param taskId - id задачи
     * @return
     */
    @Override
    public boolean delete(int taskId) {
        return 0 != crudRepository.runWithAction(
                "DELETE FROM Task t WHERE t.id = :taskId",
                Map.of("taskId", taskId)
        );
    }

    /**
     * Получить список всех задач
     *
     * @return
     */
    @Override
    public List<Task> findAll() {
        return crudRepository.query("FROM Task t JOIN FETCH t.priority", Task.class);
    }

    /**
     * Найти задачу по id
     *
     * @param taskId - id задачи
     * @return
     */
    @Override
    public Optional<Task> findById(int taskId) {
        return crudRepository.optional(
                "FROM Task t JOIN FETCH t.priority WHERE t.id = :taskId", Task.class,
                Map.of("taskId", taskId)
        );
    }

    @Override
    public List<Task> findAllByStatus(Boolean status) {
        return crudRepository.query("FROM Task t JOIN FETCH t.priority WHERE t.done = :done", Task.class,
                Map.of("done", status));
    }

    @Override
    public boolean updateStatusById(int taskId, boolean status) {
        return 0 != crudRepository.runWithAction("""
                        UPDATE Task t
                        SET t.done = :done
                        WHERE t.id = :taskId
                        """,
                Map.of("done", status, "taskId", taskId)
        );
    }
}
