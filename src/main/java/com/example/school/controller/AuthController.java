package com.example.school.controller;

import com.example.school.model.Parent;
import com.example.school.model.Student;
import com.example.school.model.Teacher;
import com.example.school.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;


@Controller
public class AuthController {
    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String login,
                        @RequestParam String password,
                        HttpSession session,
                        Model model) {
        Optional<Object> user = userService.authenticate(login, password);
        if (user.isPresent()) {
            session.setAttribute("user", user.get());

            if (user.get() instanceof Teacher) {
                return "redirect:/teacher/dashboard";
            } else if (user.get() instanceof Student) {
                return "redirect:/student/dashboard";
            } else if (user.get() instanceof Parent) {
                return "redirect:/parent/dashboard";
            }
        }

        model.addAttribute("error", "Неверный логин или пароль");
        return "login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }

    @GetMapping("/change-password")
    public String showChangePasswordForm() {
        return "change-password";
    }

    @PostMapping("/change-password")
    public String changePassword(@RequestParam String oldPassword,
                                 @RequestParam String newPassword,
                                 HttpSession session,
                                 Model model) {
        Object user = session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }

        if (userService.changePassword(user, oldPassword, newPassword)) {
            model.addAttribute("success", "Пароль успешно изменен");
        } else {
            model.addAttribute("error", "Неверный старый пароль");
        }

        return "change-password";
    }
}
