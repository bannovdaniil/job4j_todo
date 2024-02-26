package ru.job4j.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.dto.UserCreateDto;
import ru.job4j.dto.UserLoginDto;
import ru.job4j.dto.UserOutDto;
import ru.job4j.exception.UniqueConstraintException;
import ru.job4j.mapper.UserMapper;
import ru.job4j.model.User;
import ru.job4j.repository.UserRepository;
import ru.job4j.service.UserService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public Optional<User> findByLoginAndPassword(UserLoginDto userLoginDto) {
        return userRepository.findByLoginAndPassword(userLoginDto.getLogin(), userLoginDto.getPassword());
    }

    @Override
    public Optional<UserOutDto> save(UserCreateDto userDto) throws UniqueConstraintException {
        User user = userMapper.map(userDto);
        user = userRepository.save(user);

        return Optional.of(userMapper.map(user));
    }

}
