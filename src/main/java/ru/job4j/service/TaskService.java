package ru.job4j.service;

import ru.job4j.dto.TaskInDto;
import ru.job4j.dto.TaskOutDto;
import ru.job4j.dto.TaskUpdateDto;
import ru.job4j.exception.NotFoundException;

import java.util.List;

public interface TaskService {
    TaskOutDto save(TaskInDto dto);

    TaskOutDto update(TaskUpdateDto dto);

    boolean delete(int taskId) throws NotFoundException;

    List<TaskOutDto> findAll();

    TaskOutDto findById(int taskId) throws NotFoundException;

    List<TaskOutDto> findAllByStatus(Boolean status);

    TaskOutDto switchStatus(int taskId) throws NotFoundException;
}
