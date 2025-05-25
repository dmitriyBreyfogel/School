package com.example.school.controller;

import com.example.school.model.Parent;
import com.example.school.model.Student;
import com.example.school.service.DashboardService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/parent")
public class ParentController {
    private final DashboardService dashboardService;

    public ParentController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        Parent parent = (Parent) session.getAttribute("user");
        if (parent == null) {
            return "redirect:/login";
        }

        List<Student> children = parent.getListChildren();
        model.addAttribute("parent", parent);
        model.addAttribute("children", children);

        if (!children.isEmpty()) {
            Student firstChild = children.get(0);
            model.addAttribute("grades", dashboardService.getStudentGrades(firstChild.getStudentId()));
            model.addAttribute("attendance", dashboardService.getStudentAttendances(firstChild.getStudentId()));
            model.addAttribute("schedule", dashboardService.getClassSchedule(firstChild.getStudentClass().getClassId()));
        }

        return "parent/dashboard";
    }
}
