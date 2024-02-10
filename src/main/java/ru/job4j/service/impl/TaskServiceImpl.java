package ru.job4j.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.dto.TaskInDto;
import ru.job4j.dto.TaskOutDto;
import ru.job4j.dto.TaskUpdateDto;
import ru.job4j.exception.NotFoundException;
import ru.job4j.mapper.TaskMapper;
import ru.job4j.model.Task;
import ru.job4j.repository.TaskRepository;
import ru.job4j.service.TaskService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    @Override
    public Task save(TaskInDto dto) {
        return taskRepository.save(taskMapper.map(dto));
    }

    @Override
    public Task update(TaskUpdateDto dto) {
        return taskRepository.update(taskMapper.map(dto));
    }

    @Override
    public boolean delete(int taskId) {
        return taskRepository.delete(taskId);
    }

    @Override
    public List<TaskOutDto> findAll() {
        return taskMapper.map(taskRepository.findAll());
    }

    @Override
    public TaskOutDto findById(int taskId) throws NotFoundException {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(
                        () -> new NotFoundException("Task don't exist.")
                );
        return taskMapper.map(task);
    }

    @Override
    public List<TaskOutDto> findAllByStatus(Boolean status) {
        return taskMapper.map(taskRepository.findAllByStatus(status));
    }
}
