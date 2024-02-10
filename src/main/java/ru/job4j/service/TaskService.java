package ru.job4j.service;

import ru.job4j.dto.TaskInDto;
import ru.job4j.dto.TaskOutDto;
import ru.job4j.dto.TaskUpdateDto;
import ru.job4j.exception.NotFoundException;
import ru.job4j.model.Task;

import java.util.List;

public interface TaskService {
    Task save(TaskInDto dto);

    Task update(TaskUpdateDto dto);

    boolean delete(int taskId);

    List<TaskOutDto> findAll();

    TaskOutDto findById(int taskId) throws NotFoundException;

    List<TaskOutDto> findAllByStatus(Boolean status);
}
