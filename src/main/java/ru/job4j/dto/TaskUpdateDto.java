package ru.job4j.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Сущность заданий при изменении.
 * id - номер задания
 * description - описание
 * created - время создания
 * done - выполнено
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
}
