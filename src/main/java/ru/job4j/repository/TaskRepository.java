package ru.job4j.repository;

import ru.job4j.model.Task;

import java.util.List;
import java.util.Optional;

public interface TaskRepository {

    Task save(Task task);

    Task update(Task task);

    boolean delete(int taskId);

    List<Task> findAll();

    Optional<Task> findById(int taskId);

    List<Task> findAllByStatus(Boolean status);

}
