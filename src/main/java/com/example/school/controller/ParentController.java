package com.example.school.controller;

import com.example.school.dto.ScheduleDTO;
import com.example.school.model.SchoolEvent;
import com.example.school.dto.AttendanceDTO;
import com.example.school.dto.GradeDTO;
import com.example.school.model.*;
import com.example.school.service.ParentService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/parent")
public class ParentController {

    @Autowired
    private ParentService parentService;

    private boolean checkParentRole(HttpSession session) {
        String role = (String) session.getAttribute("role");
        return "parent".equals(role);
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model, HttpSession session) {
        if (!checkParentRole(session)) {
            return "redirect:/login";
        }
        Integer parentId = (Integer) session.getAttribute("userId");
        Parent parent = parentService.getParentById(parentId);
        List<Student> children = parentService.getParentChildren(parentId);
        model.addAttribute("parent", parent);
        model.addAttribute("children", children);
        return "parent/dashboard";
    }

    @GetMapping("/children")
    public String children(Model model, HttpSession session) {
        if (!checkParentRole(session)) {
            return "redirect:/login";
        }
        Integer parentId = (Integer) session.getAttribute("userId");
        Parent parent = parentService.getParentById(parentId);
        List<Student> children = parentService.getParentChildren(parentId);
        model.addAttribute("parent", parent);
        model.addAttribute("children", children);
        return "parent/children";
    }

    @GetMapping("/child/{childId}/grades")
    public String childGrades(@PathVariable("childId") Integer childId, Model model, HttpSession session) {
        if (!checkParentRole(session)) {
            return "redirect:/login";
        }
        Integer parentId = (Integer) session.getAttribute("userId");
        Parent parent = parentService.getParentById(parentId);
        List<Student> children = parentService.getParentChildren(parentId);
        if (!children.stream().anyMatch(child -> child.getStudentId().equals(childId))) {
            return "redirect:/parent/children";
        }
        Student child = children.stream().filter(c -> c.getStudentId().equals(childId)).findFirst().orElse(null);
        List<GradeDTO> grades = parentService.getChildGrades(childId);
        List<Object[]> gradesBySubject = parentService.getChildGradesBySubject(childId);
        model.addAttribute("parent", parent);
        model.addAttribute("child", child);
        model.addAttribute("grades", grades);
        model.addAttribute("gradesBySubject", gradesBySubject);
        return "parent/child-grades";
    }

    @GetMapping("/child/{childId}/attendance")
    public String childAttendance(@PathVariable("childId") Integer childId, Model model, HttpSession session) {
        if (!checkParentRole(session)) {
            return "redirect:/login";
        }
        Integer parentId = (Integer) session.getAttribute("userId");
        Parent parent = parentService.getParentById(parentId);
        List<Student> children = parentService.getParentChildren(parentId);
        if (!children.stream().anyMatch(child -> child.getStudentId().equals(childId))) {
            return "redirect:/parent/children";
        }
        Student child = children.stream().filter(c -> c.getStudentId().equals(childId)).findFirst().orElse(null);
        List<AttendanceDTO> attendance = parentService.getChildAttendance(childId);
        Object[] attendanceStats = parentService.getChildAttendanceStats(childId);
        model.addAttribute("parent", parent);
        model.addAttribute("child", child);
        model.addAttribute("attendance", attendance);
        model.addAttribute("attendanceStats", attendanceStats);
        return "parent/child-attendance";
    }

    @GetMapping("/child/{childId}/schedule")
    public String childSchedule(@PathVariable("childId") Integer childId, Model model, HttpSession session) {
        if (!checkParentRole(session)) {
            return "redirect:/login";
        }
        Integer parentId = (Integer) session.getAttribute("userId");
        Parent parent = parentService.getParentById(parentId);
        List<Student> children = parentService.getParentChildren(parentId);
        if (!children.stream().anyMatch(child -> child.getStudentId().equals(childId))) {
            return "redirect:/parent/children";
        }
        Student child = children.stream().filter(c -> c.getStudentId().equals(childId)).findFirst().orElse(null);
        List<ScheduleDTO> schedule = parentService.getChildSchedule(childId);
        model.addAttribute("parent", parent);
        model.addAttribute("child", child);
        model.addAttribute("schedule", schedule);
        return "parent/child-schedule";
    }

    @GetMapping("/teachers")
    public String teachers(Model model, HttpSession session) {
        if (!checkParentRole(session)) {
            return "redirect:/login";
        }
        Integer parentId = (Integer) session.getAttribute("userId");
        Parent parent = parentService.getParentById(parentId);
        List<Student> children = parentService.getParentChildren(parentId);
        List<Teacher> teachers = parentService.getChildTeachers(children.get(0).getStudentId());
        model.addAttribute("parent", parent);
        model.addAttribute("teachers", teachers);
        return "parent/teachers";
    }

    @GetMapping("/events")
    public String events(Model model, HttpSession session) {
        if (!checkParentRole(session)) {
            return "redirect:/login";
        }
        Integer parentId = (Integer) session.getAttribute("userId");
        Parent parent = parentService.getParentById(parentId);
        List<SchoolEvent> upcomingEvents = parentService.getUpcomingEvents();
        List<SchoolEvent> pastEvents = parentService.getPastEvents();
        model.addAttribute("parent", parent);
        model.addAttribute("upcomingEvents", upcomingEvents);
        model.addAttribute("pastEvents", pastEvents);
        return "parent/events";
    }

    private Parent getParentById(Integer parentId) {
        return parentService.getParentById(parentId);
    }
}