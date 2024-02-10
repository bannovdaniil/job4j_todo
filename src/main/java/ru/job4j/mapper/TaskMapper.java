package ru.job4j.mapper;

import org.mapstruct.Mapper;
import ru.job4j.dto.TaskInDto;
import ru.job4j.dto.TaskOutDto;
import ru.job4j.dto.TaskUpdateDto;
import ru.job4j.model.Task;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TaskMapper {
    Task map(TaskInDto dto);

    Task map(TaskUpdateDto dto);

    TaskOutDto map(Task task);

    List<TaskOutDto> map(List<Task> task);
}
