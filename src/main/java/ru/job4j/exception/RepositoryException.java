package ru.job4j.exception;

/**
 * Выбрасывается при ошибках в работе с ДБ.
 */
public class RepositoryException extends RuntimeException {
    public RepositoryException(String message) {
        super(message);
    }
}
