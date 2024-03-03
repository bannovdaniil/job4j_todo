package ru.job4j.repository.impl;

import lombok.RequiredArgsConstructor;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.stereotype.Repository;
import ru.job4j.exception.UniqueConstraintException;
import ru.job4j.model.User;
import ru.job4j.repository.UserRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {
    private final CrudRepository crudRepository;

    /**
     * Сохранить в базе.
     *
     * @param user пользователь.
     * @return пользователь с id.
     */
    public User save(User user) throws UniqueConstraintException {
        try {
            crudRepository.run(session -> session.persist(user));
        } catch (ConstraintViolationException e) {
            if (e.getMessage().contains("users_login_key")) {
                throw new UniqueConstraintException(e);
            }
        }
        return user;
    }

    /**
     * Найти пользователя по login и паролю.
     *
     * @param login    -  login
     * @param password - пароль
     * @return Optional or user.
     */
    public Optional<User> findByLoginAndPassword(String login, String password) {
        return crudRepository.optional(
                "FROM User u WHERE u.login = :login AND u.password = :password", User.class,
                Map.of("login", login, "password", password)
        );
    }

    /**
     * Удалить пользователя по id
     *
     * @param userId - id пользователя.
     * @return
     */
    @Override
    public boolean delete(int userId) {
        return 0 != crudRepository.runWithAction(
                "DELETE FROM User u WHERE u.id = :userId",
                Map.of("userId", userId)
        );
    }

    /**
     * Получить список всех пользователей
     *
     * @return
     */
    @Override
    public List<User> findAll() {
        return crudRepository.query("FROM User u", User.class);
    }

}
