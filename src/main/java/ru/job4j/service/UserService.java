package ru.job4j.service;

import ru.job4j.dto.UserCreateDto;
import ru.job4j.dto.UserLoginDto;
import ru.job4j.dto.UserOutDto;
import ru.job4j.exception.UniqueConstraintException;
import ru.job4j.model.User;

import java.util.Optional;

public interface UserService {
    Optional<User> findByLoginAndPassword(UserLoginDto userLoginDto);

    Optional<UserOutDto> save(UserCreateDto userDto) throws UniqueConstraintException;
}
