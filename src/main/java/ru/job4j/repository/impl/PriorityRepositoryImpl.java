package ru.job4j.repository.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.model.Priority;
import ru.job4j.repository.PriorityRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PriorityRepositoryImpl implements PriorityRepository {
    private final CrudRepository crudRepository;

    /**
     * Получить список всех приоритетов задач
     *
     * @return
     */
    @Override
    public List<Priority> findAll() {
        return crudRepository.query("FROM Priority p", Priority.class);
    }

    @Override
    public Optional<Priority> findById(int id) {
        return crudRepository.optional(
                "FROM Priority p WHERE p.id = :priorityId", Priority.class,
                Map.of("priorityId", id)
        );
    }

}
