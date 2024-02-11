package ru.job4j.model;

import lombok.Getter;

@Getter
public enum TaskStatus {
    DONE(true),
    TODO(false);

    private final boolean status;

    TaskStatus(boolean status) {
        this.status = status;
    }
}
