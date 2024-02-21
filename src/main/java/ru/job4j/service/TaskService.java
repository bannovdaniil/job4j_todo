package ru.job4j.service;

import ru.job4j.dto.TaskInDto;
import ru.job4j.dto.TaskOutDto;
import ru.job4j.dto.TaskUpdateDto;

import java.util.List;
import java.util.Optional;

public interface TaskService {
    TaskOutDto save(TaskInDto dto);

    TaskOutDto update(TaskUpdateDto dto);

    boolean delete(int taskId);

    List<TaskOutDto> findAll();

    Optional<TaskOutDto> findById(int taskId);

    List<TaskOutDto> findAllByStatus(Boolean status);

    boolean updateStatus(int taskId, boolean status);
}
