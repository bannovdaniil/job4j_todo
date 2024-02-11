package ru.job4j.model;

public enum TaskStatus {
    DONE(true),
    TODO(false);

    private final boolean status;

    TaskStatus(boolean status) {
        this.status = status;
    }

    public boolean isStatus() {
        return status;
    }
}
