package com.example.school.controller;

import com.example.school.model.Student;
import com.example.school.service.DashboardService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/student")
public class StudentController {
    private final DashboardService dashboardService;

    public StudentController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        Student student = (Student) session.getAttribute("user");
        if (student == null) {
            return "redirect:/login";
        }

        model.addAttribute("student", student);
        model.addAttribute("grades", dashboardService.getStudentGrades(student.getStudentId()));
        model.addAttribute("attendance", dashboardService.getStudentAttendances(student.getStudentId()));
        model.addAttribute("schedule", dashboardService.getClassSchedule(student.getStudentClass().getClassId()));
        model.addAttribute("materials", dashboardService.getAvailableMaterialsForStudent(student.getStudentId()));

        return "student/dashboard";
    }
}
