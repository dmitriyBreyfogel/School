package com.example.school.controller;

import com.example.school.service.AuthService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {

    @Autowired
    private AuthService authService;

    @GetMapping("/login")
    public String loginPage(Model model) {
        System.out.println("Here is the login page");
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, HttpSession session, Model model) {
        System.out.println(username);
        AuthService.AuthResult authResult = authService.authenticate(username, password);
        if (authResult == null) {
            model.addAttribute("error", "Неверный логин или пароль");
            return "login";
        }

        session.setAttribute("userId", authResult.getUserId());
        session.setAttribute("role", authResult.getRole());

        switch (authResult.getRole()) {
            case "teacher":
                return "redirect:/teacher/dashboard";
            case "parent":
                return "redirect:/parent/dashboard";
            case "student":
                return "redirect:/student/dashboard";
            default:
                model.addAttribute("error", "Неизвестная роль пользователя");
                return "login";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}