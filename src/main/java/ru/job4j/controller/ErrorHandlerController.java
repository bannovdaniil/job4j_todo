package ru.job4j.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.ModelAndView;
import ru.job4j.exception.NotFoundException;

@RestControllerAdvice(assignableTypes = {TaskController.class})
public class ErrorHandlerController {
    private final Logger logger = LoggerFactory.getLogger(ErrorHandlerController.class);

    @ExceptionHandler({
            MethodArgumentNotValidException.class,
            IllegalArgumentException.class
    })
    public ModelAndView handleBadRequest(final Exception e, HttpServletRequest httpRequest) {
        logger.error("url:{}, error:{}", httpRequest.getRequestURL(), e);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("errors/error");
        modelAndView.addObject("message", "Ошибка валидации данных.");
        modelAndView.setStatus(HttpStatus.BAD_REQUEST);
        return modelAndView;
    }

    @ExceptionHandler({NotFoundException.class})
    public ModelAndView handleNotFound(final Exception e, HttpServletRequest httpRequest) {
        logger.error("url:{}, error:{}", httpRequest.getRequestURL(), e);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("errors/error");
        modelAndView.addObject("message", "Сущность не найдена.");
        modelAndView.setStatus(HttpStatus.NOT_FOUND);
        return modelAndView;
    }

    @ExceptionHandler({Exception.class})
    public ModelAndView handleAllError(final Throwable e, HttpServletRequest httpRequest) {
        logger.error("url:{}, error:{}", httpRequest.getRequestURL(), e);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("errors/error");
        modelAndView.addObject("message", "Непредвиденная ошибка, сообщите администратору.");
        modelAndView.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        return modelAndView;
    }
}
