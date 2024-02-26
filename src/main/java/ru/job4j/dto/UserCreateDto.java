package ru.job4j.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Сущность Пользователя для создания.
 * name - имя
 * login - логин
 * password - пароль
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserCreateDto {
    private String name;
    private String login;
    private String password;
}
