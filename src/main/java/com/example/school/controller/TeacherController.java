package com.example.school.controller;

import com.example.school.model.Teacher;
import com.example.school.service.DashboardService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/teacher")
public class TeacherController {
    private final DashboardService dashboardService;

    public TeacherController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        Teacher teacher = (Teacher) session.getAttribute("user");
        if (teacher == null) {
            return "redirect:/login";
        }

        model.addAttribute("teacher", teacher);
        model.addAttribute("schedule", dashboardService.getTeacherSchedule(teacher.getTeacherId()));
        model.addAttribute("events", dashboardService.getUpcomingEvents());

        return "teacher/dashboard";
    }

    @GetMapping("/grades")
    public String grades(HttpSession session, Model model) {
        Teacher teacher = (Teacher) session.getAttribute("user");
        if (teacher == null) {
            return "redirect:/login";
        }

        // Здесь можно добавить логику для получения оценок по классам, которые ведет учитель
        return "teacher/grades";
    }

    @GetMapping("/attendance")
    public String attendance(HttpSession session, Model model) {
        Teacher teacher = (Teacher) session.getAttribute("user");
        if (teacher == null) {
            return "redirect:/login";
        }

        // Логика для посещаемости
        return "teacher/attendance";
    }
}
