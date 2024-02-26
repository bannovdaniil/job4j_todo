package ru.job4j.repository.impl;

import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import ru.job4j.exception.RepositoryException;
import ru.job4j.exception.UniqueConstraintException;
import ru.job4j.model.User;
import ru.job4j.repository.UserRepository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {
    private final SessionFactory sessionFactory;

    @Override
    public User save(User user) throws UniqueConstraintException {
        Session session = sessionFactory.openSession();
        try {
            Transaction transaction = session.beginTransaction();
            session.persist(user);
            transaction.commit();
        } catch (ConstraintViolationException e) {
            session.getTransaction().rollback();
            if (e.getMessage().contains("users_login_key")) {
                throw new UniqueConstraintException(e);
            }
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw new RepositoryException("Error (save): " + e.getMessage());
        } finally {
            session.close();
        }
        return user;
    }


    @Override
    public Optional<User> findByLoginAndPassword(String login, String password) {
        Optional<User> user = Optional.empty();
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            Query<User> query = session.createQuery(
                            "FROM User u WHERE u.login = :login AND u.password = :password",
                            User.class)
                    .setParameter("login", login)
                    .setParameter("password", password);
            user = query.uniqueResultOptional();
            transaction.commit();
        } catch (Exception e) {
            throw new RepositoryException("Error (findById): " + e.getMessage());
        }
        return user;
    }

}
