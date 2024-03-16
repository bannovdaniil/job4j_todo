package ru.job4j.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.dto.TaskInDto;
import ru.job4j.dto.TaskOutDto;
import ru.job4j.dto.TaskUpdateDto;
import ru.job4j.mapper.TaskMapper;
import ru.job4j.model.Task;
import ru.job4j.model.User;
import ru.job4j.repository.PriorityRepository;
import ru.job4j.repository.TaskRepository;
import ru.job4j.service.TaskService;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.TimeZone;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final PriorityRepository priorityRepository;
    private final TaskMapper taskMapper;

    @Override
    public TaskOutDto save(TaskInDto dto, User userLogged) {
        Task task = taskMapper.map(dto);
        task.setDone(false);
        task.setUser(userLogged);
        task.setCreated(LocalDateTime.now(ZoneId.of("UTC")));
        task.setPriority(priorityRepository.findById(task.getPriority().getId()).get());

        task = fixTimeZone(taskRepository.save(task));
        return taskMapper.map(task);
    }

    @Override
    public TaskOutDto update(TaskUpdateDto dto) {
        Task task = taskMapper.map(dto);
        User taskUser = taskRepository.findById(task.getId()).get().getUser();
        task.setUser(taskUser);
        taskRepository.update(task);

        task = fixTimeZone(taskRepository.findById(task.getId()).get());
        return taskMapper.map(task);
    }

    @Override
    public boolean delete(int taskId) {
        return taskRepository.delete(taskId);
    }

    @Override
    public List<TaskOutDto> findAll() {
        List<Task> taskList = taskRepository.findAll();
        taskList.forEach(this::fixTimeZone);
        return taskMapper.map(taskList);
    }

    @Override
    public Optional<TaskOutDto> findById(int taskId) {
        Optional<Task> task = taskRepository.findById(taskId);
        task = fixTimeZone(task);
        return task.map(taskMapper::map);
    }

    @Override
    public List<TaskOutDto> findAllByStatus(Boolean status) {
        List<Task> taskList = taskRepository.findAllByStatus(status);
        taskList.forEach(this::fixTimeZone);
        return taskMapper.map(taskList);
    }

    @Override
    public boolean updateStatus(int taskId, boolean status) {
        return taskRepository.updateStatusById(taskId, status);
    }

    public Optional<Task> fixTimeZone(Optional<Task> task) {
        return task.map(this::fixTimeZone);
    }

    public Task fixTimeZone(Task task) {
        String timezone = task.getUser().getTimezone();
        if (timezone == null || timezone.isEmpty()) {
            timezone = TimeZone.getDefault().getID();
        }

        ZonedDateTime localTime = task.getCreated()
                .atZone(ZoneId.of("UTC"))
                .withZoneSameInstant(ZoneId.of(timezone));
        task.setCreated(localTime.toLocalDateTime());

        return task;
    }
}
