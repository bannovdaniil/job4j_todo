package ru.job4j.repository.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.model.Category;
import ru.job4j.repository.CategoryRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CategoryRepositoryImpl implements CategoryRepository {
    private final CrudRepository crudRepository;

    /**
     * Получить список всех приоритетов задач
     *
     * @return
     */
    @Override
    public List<Category> findAll() {
        return crudRepository.query("FROM Category p", Category.class);
    }

    @Override
    public Optional<Category> findById(int id) {
        return crudRepository.optional(
                "FROM Category c WHERE c.id = :categoryId", Category.class,
                Map.of("categoryId", id)
        );
    }

}
