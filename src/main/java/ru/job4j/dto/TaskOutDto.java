package ru.job4j.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Сущность заданий при отображении информации.
 * id - номер задания
 * description - описание
 * created - время создания
 * done - выполнено
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TaskOutDto {
    private int id;
    private String title;
    private String description;
    private LocalDateTime created;
    private UserOutDto user;
    private boolean done;
}
