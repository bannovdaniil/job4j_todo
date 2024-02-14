package ru.job4j.repository.impl;

import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import ru.job4j.exception.RepositoryException;
import ru.job4j.model.Task;
import ru.job4j.repository.TaskRepository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class TaskRepositoryImpl implements TaskRepository {
    private final SessionFactory sessionFactory;

    @Override
    public Task save(Task task) {
        Session session = sessionFactory.openSession();
        try {
            Transaction transaction = session.beginTransaction();
            session.persist(task);
            transaction.commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw new RepositoryException("Error (save): " + e.getMessage());
        } finally {
            session.close();
        }
        return task;
    }

    @Override
    public Task update(Task task) {
        Session session = sessionFactory.openSession();
        try {
            Transaction transaction = session.beginTransaction();
            Query query = session.createQuery("""
                            UPDATE Task t
                            SET t.title = :title,
                                t.description = :description,
                                t.done = :done
                            WHERE t.id = :taskId
                            """)
                    .setParameter("title", task.getTitle())
                    .setParameter("description", task.getDescription())
                    .setParameter("done", task.isDone())
                    .setParameter("taskId", task.getId());
            query.executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw new RepositoryException("Error (update): " + e.getMessage());
        } finally {
            session.close();
        }

        return task;
    }

    @Override
    public boolean delete(int taskId) {
        boolean result;
        Session session = sessionFactory.openSession();
        try {
            Transaction transaction = session.beginTransaction();
            Query query = session.createQuery("DELETE Task t WHERE t.id = :taskId")
                    .setParameter("taskId", taskId);
            query.executeUpdate();
            transaction.commit();
            result = true;
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw new RepositoryException("Error (delete): " + e.getMessage());
        } finally {
            session.close();
        }
        return result;
    }

    @Override
    public List<Task> findAll() {
        List<Task> taskList = List.of();
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            Query<Task> query = session.createQuery("FROM Task t", Task.class);
            taskList = query.list();
            transaction.commit();
        } catch (Exception e) {
            throw new RepositoryException("Error (findAll): " + e.getMessage());
        }
        return taskList;
    }

    @Override
    public Optional<Task> findById(int taskId) {
        Optional<Task> task = Optional.empty();
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            Query<Task> query = session.createQuery("FROM Task t WHERE t.id = :taskId", Task.class)
                    .setParameter("taskId", taskId);
            task = query.uniqueResultOptional();
            transaction.commit();
        } catch (Exception e) {
            throw new RepositoryException("Error (findById): " + e.getMessage());
        }
        return task;
    }

    @Override
    public List<Task> findAllByStatus(Boolean status) {
        List<Task> taskList = List.of();
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            Query<Task> query = session.createQuery("FROM Task t WHERE t.done = :done", Task.class)
                    .setParameter("done", status);
            taskList = query.list();
            transaction.commit();
        } catch (Exception e) {
            throw new RepositoryException("Error (findAllByStatus): " + e.getMessage());
        }
        return taskList;
    }

    @Override
    public Optional<Task> updateStatusById(int taskId, boolean status) {
        Optional<Task> task = Optional.empty();
        Session session = sessionFactory.openSession();
        try {
            Transaction transaction = session.beginTransaction();
            Query query = session.createQuery("""
                            UPDATE Task t
                            SET t.done = :done
                            WHERE t.id = :taskId
                            """)
                    .setParameter("done", status)
                    .setParameter("taskId", taskId);
            query.executeUpdate();
            Query<Task> getQuery = session.createQuery("FROM Task t WHERE t.id = :taskId", Task.class)
                    .setParameter("taskId", taskId);
            task = getQuery.uniqueResultOptional();
            transaction.commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw new RepositoryException("Error (update): " + e.getMessage());
        } finally {
            session.close();
        }
        return task;
    }
}
