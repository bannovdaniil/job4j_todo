package ru.job4j.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.dto.TaskInDto;
import ru.job4j.dto.TaskOutDto;
import ru.job4j.dto.TaskUpdateDto;
import ru.job4j.exception.NotFoundException;
import ru.job4j.model.TaskStatus;
import ru.job4j.service.TaskService;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;

    private static String sendNotFoundError(Model model, String message) {
        model.addAttribute("message", message);
        return "errors/404";
    }

    /**
     * Стартовая страница - список задач
     */
    @GetMapping("/")
    public String getList(Model model) {
        model.addAttribute("tasks", taskService.findAll());
        return "tasks/list";
    }

    /**
     * Стартовая страница - список задач
     */
    @GetMapping("/tasks/status/{status}")
    public String getListForStatus(@PathVariable String status, Model model) {
        TaskStatus taskStatus = TaskStatus.valueOf(status.toUpperCase());
        model.addAttribute("tasks", taskService.findAllByStatus(taskStatus.isStatus()));
        model.addAttribute("status", taskStatus.name());
        return "tasks/list";
    }

    /**
     * Перевести задачу в противоположное состояние
     * done -> to_do
     * to_do -> done
     */
    @GetMapping("/tasks/switch/{taskId}")
    public String switchStatus(@PathVariable int taskId, Model model) {
        try {
            TaskOutDto dto = taskService.switchStatus(taskId);
            model.addAttribute("task", dto);
        } catch (Exception e) {
            return sendNotFoundError(model, "Не смог поменять состояние.");
        }
        return "tasks/one";
    }

    /**
     * Удалить задачу
     */
    @GetMapping("/tasks/delete/{taskId}")
    public String deleteTask(@PathVariable int taskId, Model model) {
        try {
            taskService.delete(taskId);
        } catch (Exception e) {
            return sendNotFoundError(model, "Не смог удалить задачу.");
        }
        return "redirect:/";
    }

    /**
     * Показать страницу для редактирования задачи
     */
    @GetMapping("/tasks/edit/{taskId}")
    public String showEditTaskPage(@PathVariable int taskId, Model model) {
        try {
            TaskOutDto task = taskService.findById(taskId);
            model.addAttribute("task", task);
        } catch (Exception e) {
            return sendNotFoundError(model, "Не смог найти задачу.");
        }
        return "tasks/edit";
    }

    /**
     * Редактирование задачи
     */
    @PostMapping("/tasks/edit")
    public String editTask(@ModelAttribute TaskUpdateDto dto, Model model) {
        try {
            TaskOutDto task = taskService.update(dto);
            model.addAttribute("task", task);
        } catch (Exception e) {
            return sendNotFoundError(model, "Не смог найти задачу.");
        }
        return "tasks/one";
    }

    /**
     * Страница с формой для создания задачи.
     */
    @GetMapping("/tasks/add")
    public String addTaskPage() {
        return "tasks/add";
    }

    /**
     * Показать задачу по ID.
     */
    @GetMapping("/tasks/{taskId}")
    public String addTask(@PathVariable Integer taskId, Model model) {
        TaskOutDto task;
        try {
            task = taskService.findById(taskId);
            model.addAttribute("task", task);
        } catch (NotFoundException e) {
            return sendNotFoundError(model, "Задача не не найдена.");
        }
        return "tasks/one";
    }

    /**
     * Создания задачи.
     */
    @PostMapping("/tasks/add")
    public String addTask(@ModelAttribute TaskInDto dto, Model model) {
        TaskOutDto task;

        try {
            task = taskService.save(dto);
            model.addAttribute("task", task);
        } catch (Exception e) {
            model.addAttribute("error", "Не удалось создать задачу.");
            return "tasks/add";
        }

        return "tasks/one";
    }

}
