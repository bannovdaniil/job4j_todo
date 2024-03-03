package ru.job4j.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.dto.TaskInDto;
import ru.job4j.dto.TaskOutDto;
import ru.job4j.dto.TaskUpdateDto;
import ru.job4j.mapper.TaskMapper;
import ru.job4j.model.Task;
import ru.job4j.model.User;
import ru.job4j.repository.TaskRepository;
import ru.job4j.service.TaskService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    @Override
    public TaskOutDto save(TaskInDto dto, User userLogged) {
        Task task = taskMapper.map(dto);
        task.setDone(false);
        task.setUser(userLogged);
        task.setCreated(LocalDateTime.now());

        return taskMapper.map(taskRepository.save(task));
    }

    @Override
    public TaskOutDto update(TaskUpdateDto dto) {
        Task task = taskMapper.map(dto);
        taskRepository.update(task);
        return taskMapper.map(taskRepository.findById(task.getId()).get());
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
    public Optional<TaskOutDto> findById(int taskId) {
        Optional<Task> task = taskRepository.findById(taskId);
        return task.map(taskMapper::map);
    }

    @Override
    public List<TaskOutDto> findAllByStatus(Boolean status) {
        return taskMapper.map(taskRepository.findAllByStatus(status));
    }

    @Override
    public boolean updateStatus(int taskId, boolean status) {
        return taskRepository.updateStatusById(taskId, status);
    }
}
