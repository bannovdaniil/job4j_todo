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
 * Сущность заданий при изменении.
 * id - номер задания
 * description - описание
 * created - время создания
 * done - выполнено
 * priority - приоритет задания
 * categories - категории задания.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TaskUpdateDto {
    private Integer id;
    private String title;
    private String description;
    private Boolean done;
    private Priority priority;
    private Set<Category> categories = new HashSet<>();
}
