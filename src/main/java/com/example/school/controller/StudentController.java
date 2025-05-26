package com.example.school.controller;

import com.example.school.dto.AttendanceDTO;
import com.example.school.dto.GradeDTO;
import com.example.school.dto.ScheduleDTO;
import com.example.school.model.Student;
import com.example.school.service.StudentService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private StudentService studentService;

    private boolean checkStudentRole(HttpSession session) {
        String role = (String) session.getAttribute("role");
        return "student".equals(role);
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model, HttpSession session) {
        if (!checkStudentRole(session)) {
            return "redirect:/login";
        }
        Integer studentId = (Integer) session.getAttribute("userId");
        Student student = studentService.getStudentById(studentId);
        List<ScheduleDTO> todaySchedule = studentService.getTodaySchedule(studentId);
        model.addAttribute("student", student);
        model.addAttribute("todaySchedule", todaySchedule);
        return "student/dashboard";
    }

    @GetMapping("/grades")
    public String grades(Model model, HttpSession session) {
        if (!checkStudentRole(session)) {
            return "redirect:/login";
        }
        Integer studentId = (Integer) session.getAttribute("userId");
        Student student = studentService.getStudentById(studentId);
        List<GradeDTO> grades = studentService.getStudentGrades(studentId);
        List<Object[]> gradesBySubject = studentService.getGradesBySubject(studentId);
        model.addAttribute("student", student);
        model.addAttribute("grades", grades);
        model.addAttribute("gradesBySubject", gradesBySubject);
        return "student/grades";
    }

    @GetMapping("/attendance")
    public String attendance(Model model, HttpSession session) {
        if (!checkStudentRole(session)) {
            return "redirect:/login";
        }
        Integer studentId = (Integer) session.getAttribute("userId");
        Student student = studentService.getStudentById(studentId);
        List<AttendanceDTO> attendance = studentService.getStudentAttendance(studentId);
        Object[] attendanceStats = studentService.getAttendanceStats(studentId);
        model.addAttribute("student", student);
        model.addAttribute("attendance", attendance);
        model.addAttribute("attendanceStats", attendanceStats);
        return "student/attendance";
    }

    @GetMapping("/schedule")
    public String schedule(Model model, HttpSession session) {
        if (!checkStudentRole(session)) {
            return "redirect:/login";
        }
        Integer studentId = (Integer) session.getAttribute("userId");
        Student student = studentService.getStudentById(studentId);
        List<ScheduleDTO> schedule = studentService.getStudentSchedule(studentId);
        model.addAttribute("student", student);
        model.addAttribute("schedule", schedule);
        return "student/schedule";
    }

    private Student getStudentById(Integer studentId) {
        return studentService.getStudentById(studentId);
    }
}