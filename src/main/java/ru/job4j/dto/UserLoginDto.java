package ru.job4j.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Сущность Пользователя для логина.
 * login - логин
 * password - пароль
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserLoginDto {
    private String login;
    private String password;
}
