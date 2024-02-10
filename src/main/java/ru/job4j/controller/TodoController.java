package ru.job4j.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class TodoController {

    private static String sendNotFoundError(Model model, String message) {
        model.addAttribute("message", message);
        return "errors/404";
    }

    @GetMapping("/")
    public String getIndex() {
        return "/index";
    }
}
