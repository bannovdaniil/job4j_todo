package ru.job4j.repository.impl;

import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.ClassLoaderResourceAccessor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.internal.SessionImpl;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import ru.job4j.exception.UniqueConstraintException;
import ru.job4j.model.Task;
import ru.job4j.model.User;
import ru.job4j.repository.TaskRepository;
import ru.job4j.repository.UserRepository;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Properties;

class TaskRepositoryImplTest {
    private static TaskRepository taskRepository;
    private static UserRepository userRepository;

    @BeforeAll
    public static void initRepositories() throws Exception {
        var properties = new Properties();
        try (var inputStream = TaskRepositoryImpl.class.getClassLoader().getResourceAsStream("db/liquibase_test.properties")) {
            properties.load(inputStream);
        }
        String liquibaseSchema = properties.getProperty("changeLogFile");

        StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure("hibernate_test.cfg.xml")
                .build();

        SessionFactory sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        CrudRepository crudRepository = new CrudRepository(sessionFactory);
        initLiquibase(sessionFactory, liquibaseSchema);
        taskRepository = new TaskRepositoryImpl(crudRepository);
        userRepository = new UserRepositoryImpl(crudRepository);
    }

    public static void initLiquibase(SessionFactory sessionFactory, String defaultLiquibaseChangelog) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Connection connection = ((SessionImpl) session).getJdbcConnectionAccess().obtainConnection();
            Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));
            Liquibase liquibase = new Liquibase(defaultLiquibaseChangelog, new ClassLoaderResourceAccessor(), database);
            liquibase.dropAll();
            liquibase.update();
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @AfterEach
    void wipeTable() throws SQLException {
        taskRepository.findAll().forEach(
                task -> taskRepository.delete(task.getId())
        );
        userRepository.findAll().forEach(
                user -> userRepository.delete(user.getId())
        );
    }

    @DisplayName("Save + findById")
    @Test
    void save() throws UniqueConstraintException {
        User user = new User(0, "testUser", "login", "password");
        user = userRepository.save(user);
        Task task = new Task(0, "task name", "test task", LocalDateTime.now(), false, user);
        task = taskRepository.save(task);
        Task resultTask = taskRepository.findById(task.getId()).orElseThrow();

        Assertions.assertEquals(task.getId(), resultTask.getId());
    }

    @DisplayName("update description, status, BUT not CreateTime")
    @Test
    void update() throws UniqueConstraintException {
        String expectedDescription = "Update Description";
        LocalDateTime expectedTime = LocalDateTime.now().withNano(0);
        boolean expectedStatus = true;

        User user = new User(0, "testUser", "login", "password");
        user = userRepository.save(user);
        Task task = new Task(0, "task name", "test task", expectedTime, false, user);
        task = taskRepository.save(task);
        task = taskRepository.findById(task.getId()).orElseThrow();

        task.setCreated(LocalDateTime.now());
        task.setDescription(expectedDescription);
        task.setDone(expectedStatus);

        taskRepository.update(task);
        task = taskRepository.findById(task.getId()).orElseThrow();

        Assertions.assertEquals(expectedDescription, task.getDescription());
        Assertions.assertEquals(expectedStatus, task.isDone());
        Assertions.assertEquals(expectedTime, task.getCreated());
    }

    @Test
    void delete() throws UniqueConstraintException {
        User user = new User(0, "testUser", "login", "password");
        user = userRepository.save(user);
        Task task = new Task(0, "task name", "test task", LocalDateTime.now(), false, user);

        task = taskRepository.save(task);
        Assertions.assertTrue(taskRepository.findById(task.getId()).isPresent());

        taskRepository.delete(task.getId());
        Assertions.assertTrue(taskRepository.findById(task.getId()).isEmpty());
    }

    @Test
    void findAll() throws UniqueConstraintException {
        int beforeSize = taskRepository.findAll().size();
        User user = new User(0, "testUser", "login", "password");
        user = userRepository.save(user);
        Task task = new Task(0, "task name", "test task", LocalDateTime.now(), false, user);
        task = taskRepository.save(task);

        int afterSize = taskRepository.findAll().size();

        Assertions.assertNotEquals(beforeSize, afterSize);
        Assertions.assertEquals(beforeSize + 1, afterSize);
    }

    @ParameterizedTest
    @CsvSource({
            "true",
            "false"
    })
    void findAllByStatus(Boolean status) throws UniqueConstraintException {
        int beforeSize = taskRepository.findAllByStatus(status).size();
        User user = new User(0, "testUser", "login", "password");
        user = userRepository.save(user);
        Task task = new Task(0, "task name", "test task", LocalDateTime.now(), status, user);
        task = taskRepository.save(task);

        int afterSize = taskRepository.findAllByStatus(status).size();

        Assertions.assertNotEquals(beforeSize, afterSize);
        Assertions.assertEquals(beforeSize + 1, afterSize);
    }
}