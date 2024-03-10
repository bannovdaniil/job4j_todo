package ru.job4j.repository;

import ru.job4j.model.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository {
    List<Category> findAll();

    Optional<Category> findById(int id);
}
