package ru.job4j.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.dto.TaskInDto;
import ru.job4j.dto.TaskOutDto;
import ru.job4j.dto.TaskUpdateDto;
import ru.job4j.model.User;
import ru.job4j.service.CategoryService;
import ru.job4j.service.PriorityService;
import ru.job4j.service.TaskService;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;
    private final PriorityService priorityService;
    private final CategoryService categoryService;

    /**
     * Стартовая страница - список задач
     */
    @GetMapping("/")
    public String getList(Model model) {
        List<TaskOutDto> all = taskService.findAll();
        model.addAttribute("tasks", all);
        return "tasks/list";
    }

    /**
     * Стартовая страница - список задач
     */
    @GetMapping("/tasks/status/{status}")
    public String getListForStatus(@PathVariable String status, Model model) {
        model.addAttribute("tasks", taskService.findAllByStatus(status.equals("done")));
        model.addAttribute("status", status);
        return "tasks/list";
    }

    /**
     * Перевести задачу в противоположное состояние
     * done -> to_do
     * to_do -> done
     */
    @GetMapping("/tasks/switch/{taskId}/done")
    public String switchStatusToDone(@PathVariable int taskId, Model model) {
        if (!taskService.updateStatus(taskId, true)) {
            model.addAttribute("message", "Ошибка обновления статуса");
            return "errors/error";
        }
        return "redirect:/tasks/" + taskId;
    }

    /**
     * Перевести задачу в противоположное состояние
     * done -> to_do
     * to_do -> done
     */
    @GetMapping("/tasks/switch/{taskId}/todo")
    public String switchStatusToTodo(@PathVariable int taskId, Model model) {
        if (!taskService.updateStatus(taskId, false)) {
            model.addAttribute("message", "Ошибка обновления статуса");
            return "errors/error";
        }
        return "redirect:/tasks/" + taskId;
    }

    /**
     * Удалить задачу
     */
    @GetMapping("/tasks/delete/{taskId}")
    public String deleteTask(@PathVariable int taskId, Model model) {
        if (!taskService.delete(taskId)) {
            model.addAttribute("message", "Не смог удалить.");
            return "errors/error";
        }
        return "redirect:/";
    }

    /**
     * Показать страницу для редактирования задачи
     */
    @GetMapping("/tasks/edit/{taskId}")
    public String showEditTaskPage(@PathVariable int taskId, Model model) {
        Optional<TaskOutDto> dto = taskService.findById(taskId);
        if (dto.isEmpty()) {
            model.addAttribute("message", "Задача не найдена.");
            return "errors/error";
        }
        model.addAttribute("task", dto.get());
        model.addAttribute("priorities", priorityService.findAll());
        model.addAttribute("categories", categoryService.findAll());
        return "tasks/edit";
    }

    /**
     * Редактирование задачи
     */
    @PostMapping("/tasks/edit")
    public String editTask(@ModelAttribute TaskUpdateDto dto, Model model) {
        TaskOutDto task = taskService.update(dto);
        model.addAttribute("task", task);
        return "tasks/one";
    }

    /**
     * Показать задачу по ID.
     */
    @GetMapping("/tasks/{taskId}")
    public String getTaskByIdTask(@PathVariable Integer taskId, Model model) {
        Optional<TaskOutDto> dto = taskService.findById(taskId);
        if (dto.isEmpty()) {
            model.addAttribute("message", "Задача не найдена.");
            return "errors/error";
        }
        model.addAttribute("task", dto.get());
        return "tasks/one";
    }

    /**
     * Страница с формой для создания задачи.
     */
    @GetMapping("/tasks/add")
    public String addTaskPage(Model model) {
        model.addAttribute("priorities", priorityService.findAll());
        model.addAttribute("categories", categoryService.findAll());
        return "tasks/add";
    }

    /**
     * Создания задачи.
     */
    @PostMapping("/tasks/add")
    public String addTask(@ModelAttribute TaskInDto dto,
                          Model model,
                          @SessionAttribute(name = "userLogged") User user) {
        TaskOutDto task;

        try {
            task = taskService.save(dto, user);
            model.addAttribute("task", task);
        } catch (Exception e) {
            model.addAttribute("error", "Не удалось создать задачу.");
            return "tasks/add";
        }

        return "tasks/one";
    }
}
