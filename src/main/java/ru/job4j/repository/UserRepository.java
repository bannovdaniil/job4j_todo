package ru.job4j.repository;

import ru.job4j.exception.UniqueConstraintException;
import ru.job4j.model.User;

import java.util.Optional;

public interface UserRepository {
    User save(User user) throws UniqueConstraintException;
    Optional<User> findByLoginAndPassword(String login, String password);
}
