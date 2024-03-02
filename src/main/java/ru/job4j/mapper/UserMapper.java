package ru.job4j.mapper;

import org.mapstruct.Mapper;
import ru.job4j.dto.UserCreateDto;
import ru.job4j.dto.UserOutDto;
import ru.job4j.model.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User map(UserCreateDto dto);

    UserOutDto map(User user);
}
