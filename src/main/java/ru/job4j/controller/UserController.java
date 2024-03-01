package ru.job4j.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.job4j.dto.UserCreateDto;
import ru.job4j.dto.UserLoginDto;
import ru.job4j.dto.UserOutDto;
import ru.job4j.exception.UniqueConstraintException;
import ru.job4j.model.User;
import ru.job4j.service.UserService;

import java.util.Optional;

/**
 * Ручка для работы с Пользователями
 */
@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * Страница ввода данных для авторизации пользователя.
     */
    @GetMapping("/login")
    public String getLoginPage() {
        return "users/login";
    }

    @PostMapping("/login")
    public String loginUser(@ModelAttribute UserLoginDto userLoginDto, Model model, HttpServletRequest request) {
        Optional<User> userOptional = userService.findByLoginAndPassword(userLoginDto);
        if (userOptional.isEmpty()) {
            model.addAttribute("error", "Логин или пароль введены неверно");
            return "users/login";
        }
        HttpSession session = request.getSession();
        session.setAttribute("userLogged", userOptional.get());

        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/users/login";
    }

    @GetMapping("/register")
    public String getRegistrationPage() {
        return "users/register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute UserCreateDto dto, Model model) {
        Optional<UserOutDto> user;

        try {
            user = userService.save(dto);
            model.addAttribute("userLogged", user.get());
        } catch (UniqueConstraintException e) {
            model.addAttribute("error", "Пользователь с таким Логином уже существует.");
            return "users/register";
        } catch (Exception e) {
            model.addAttribute("error", "Не удалось создать пользователя.");
            return "users/register";
        }

        return "redirect:/users/login";
    }
}
