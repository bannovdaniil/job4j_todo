package ru.job4j.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.job4j.model.Category;
import ru.job4j.model.Priority;

import java.util.HashSet;
import java.util.Set;

/**
 * Сущность заданий для формы создания.
 * title - название задачи
 * description - описание
 * priority - приоритет задания
 * categories - категории задания.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TaskInDto {
    private String title;
    private String description;
    private Priority priority;
    private Set<Category> categories = new HashSet<>();
}
