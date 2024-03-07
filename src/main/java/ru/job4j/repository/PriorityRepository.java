package ru.job4j.repository;

import ru.job4j.model.Priority;

import java.util.List;
import java.util.Optional;

public interface PriorityRepository {
    List<Priority> findAll();

    Optional<Priority> findById(int id);
}
