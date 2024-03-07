package ru.job4j.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.job4j.model.Priority;

/**
 * Сущность заданий для формы создания.
 * title - название задачи
 * description - описание
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TaskInDto {
    private String title;
    private String description;
    private Priority priority;
}
