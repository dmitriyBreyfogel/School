package com.example.school.controller;

import com.example.school.dto.AttendanceDTO;
import com.example.school.dto.GradeDTO;
import com.example.school.dto.ScheduleDTO;
import com.example.school.dto.StudentAnalyticsDTO;
import com.example.school.model.*;
import com.example.school.service.AnalyticsService;
import com.example.school.service.TeacherService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/teacher")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private AnalyticsService analyticsService;

    private boolean checkTeacherRole(HttpSession session) {
        String role = (String) session.getAttribute("role");
        return "teacher".equals(role);
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model, HttpSession session) {
        if (!checkTeacherRole(session)) {
            return "redirect:/login";
        }
        Integer teacherId = (Integer) session.getAttribute("userId");
        Teacher teacher = teacherService.getTeacherById(teacherId);
        List<ScheduleDTO> todaySchedule = teacherService.getTodaySchedule(teacherId);
        model.addAttribute("teacher", teacher);
        model.addAttribute("todaySchedule", todaySchedule);
        return "teacher/dashboard";
    }

    @GetMapping("/schedule")
    public String schedule(Model model, HttpSession session) {
        if (!checkTeacherRole(session)) {
            return "redirect:/login";
        }
        Integer teacherId = (Integer) session.getAttribute("userId");
        Teacher teacher = teacherService.getTeacherById(teacherId);
        List<ScheduleDTO> weekSchedule = teacherService.getWeekSchedule(teacherId);
        model.addAttribute("teacher", teacher);
        model.addAttribute("weekSchedule", weekSchedule);
        return "teacher/schedule";
    }

    @GetMapping("/grades")
    public String grades(Model model, HttpSession session) {
        if (!checkTeacherRole(session)) {
            return "redirect:/login";
        }
        Integer teacherId = (Integer) session.getAttribute("userId");
        Teacher teacher = teacherService.getTeacherById(teacherId);
        List<SchoolClass> classes = teacherService.getTeacherClasses(teacherId);
        model.addAttribute("teacher", teacher);
        model.addAttribute("classes", classes);
        return "teacher/grades";
    }

    @PostMapping("/grades")
    public String gradesForClass(@RequestParam("classId") Integer classId, Model model, HttpSession session) {
        if (!checkTeacherRole(session)) {
            return "redirect:/login";
        }
        Integer teacherId = (Integer) session.getAttribute("userId");
        Teacher teacher = teacherService.getTeacherById(teacherId);
        List<GradeDTO> grades = teacherService.getClassGrades(classId, teacherId);
        List<SchoolClass> classes = teacherService.getTeacherClasses(teacherId);
        model.addAttribute("teacher", teacher);
        model.addAttribute("classes", classes);
        model.addAttribute("grades", grades);
        model.addAttribute("classId", classId);
        return "teacher/grades";
    }

    @PostMapping("/add-grade")
    public String addGrade(@ModelAttribute GradeDTO gradeDTO, @RequestParam("classId") Integer classId, HttpSession session) {
        if (!checkTeacherRole(session)) {
            return "redirect:/login";
        }
        Integer teacherId = (Integer) session.getAttribute("userId");
        teacherService.addGrade(gradeDTO, teacherId);
        return "redirect:/teacher/grades?classId=" + classId;
    }

    @PostMapping("/update-grade")
    public String updateGrade(@ModelAttribute GradeDTO gradeDTO, @RequestParam("classId") Integer classId, HttpSession session) {
        if (!checkTeacherRole(session)) {
            return "redirect:/login";
        }
        Integer teacherId = (Integer) session.getAttribute("userId");
        teacherService.updateGrade(gradeDTO, teacherId);
        return "redirect:/teacher/grades?classId=" + classId;
    }

    @PostMapping("/delete-grade")
    public String deleteGrade(@RequestParam("gradeId") Integer gradeId, @RequestParam("classId") Integer classId, HttpSession session) {
        if (!checkTeacherRole(session)) {
            return "redirect:/login";
        }
        Integer teacherId = (Integer) session.getAttribute("userId");
        teacherService.deleteGrade(gradeId, teacherId);
        return "redirect:/teacher/grades?classId=" + classId;
    }

    @GetMapping("/attendance")
    public String attendance(Model model, HttpSession session) {
        if (!checkTeacherRole(session)) {
            return "redirect:/login";
        }
        Integer teacherId = (Integer) session.getAttribute("userId");
        Teacher teacher = teacherService.getTeacherById(teacherId);
        List<SchoolClass> classes = teacherService.getTeacherClasses(teacherId);
        model.addAttribute("teacher", teacher);
        model.addAttribute("classes", classes);
        return "teacher/attendance";
    }

    @PostMapping("/attendance")
    public String attendanceForClass(@RequestParam("classId") Integer classId, @RequestParam("date") String date,
                                     Model model, HttpSession session) {
        if (!checkTeacherRole(session)) {
            return "redirect:/login";
        }
        Integer teacherId = (Integer) session.getAttribute("userId");
        Teacher teacher = teacherService.getTeacherById(teacherId);
        List<AttendanceDTO> attendance = teacherService.getClassAttendance(classId, date, teacherId);
        List<SchoolClass> classes = teacherService.getTeacherClasses(teacherId);
        model.addAttribute("teacher", teacher);
        model.addAttribute("classes", classes);
        model.addAttribute("attendance", attendance);
        model.addAttribute("classId", classId);
        model.addAttribute("date", date);
        return "teacher/attendance";
    }

    @PostMapping("/mark-attendance")
    public String markAttendance(@RequestParam("attendance") List<AttendanceDTO> attendanceList,
                                 @RequestParam("classId") Integer classId, @RequestParam("date") String date,
                                 HttpSession session) {
        if (!checkTeacherRole(session)) {
            return "redirect:/login";
        }
        Integer teacherId = (Integer) session.getAttribute("userId");
        teacherService.markAttendance(attendanceList, teacherId);
        return "redirect:/teacher/attendance?classId=" + classId + "&date=" + date;
    }

    @PostMapping("/update-attendance")
    public String updateAttendance(@ModelAttribute AttendanceDTO attendanceDTO, @RequestParam("classId") Integer classId,
                                   @RequestParam("date") String date, HttpSession session) {
        if (!checkTeacherRole(session)) {
            return "redirect:/login";
        }
        Integer teacherId = (Integer) session.getAttribute("userId");
        teacherService.updateAttendance(attendanceDTO, teacherId);
        return "redirect:/teacher/attendance?classId=" + classId + "&date=" + date;
    }

    @PostMapping("/delete-attendance")
    public String deleteAttendance(@RequestParam("attendanceId") Integer attendanceId, @RequestParam("classId") Integer classId,
                                   @RequestParam("date") String date, HttpSession session) {
        if (!checkTeacherRole(session)) {
            return "redirect:/login";
        }
        Integer teacherId = (Integer) session.getAttribute("userId");
        teacherService.deleteAttendance(attendanceId, teacherId);
        return "redirect:/teacher/attendance?classId=" + classId + "&date=" + date;
    }

    @GetMapping("/materials")
    public String materials(Model model, HttpSession session) {
        if (!checkTeacherRole(session)) {
            return "redirect:/login";
        }
        Integer teacherId = (Integer) session.getAttribute("userId");
        Teacher teacher = teacherService.getTeacherById(teacherId);
        List<LearningMaterial> materials = teacherService.getTeacherMaterials(teacherId);
        model.addAttribute("teacher", teacher);
        model.addAttribute("materials", materials);
        return "teacher/materials";
    }

    @GetMapping("/analytics")
    public String analytics(Model model, HttpSession session) {
        if (!checkTeacherRole(session)) {
            return "redirect:/login";
        }
        Integer teacherId = (Integer) session.getAttribute("userId");
        Teacher teacher = teacherService.getTeacherById(teacherId);
        List<SchoolClass> classes = teacherService.getTeacherClasses(teacherId);
        model.addAttribute("teacher", teacher);
        model.addAttribute("classes", classes);
        return "teacher/analytics";
    }

    @PostMapping("/analytics")
    public String analyticsForClass(@RequestParam("classId") Integer classId, Model model, HttpSession session) {
        if (!checkTeacherRole(session)) {
            return "redirect:/login";
        }
        Integer teacherId = (Integer) session.getAttribute("userId");
        Teacher teacher = teacherService.getTeacherById(teacherId);
        List<StudentAnalyticsDTO> analytics = analyticsService.getStudentPerformanceByClass(classId);
        List<SchoolClass> classes = teacherService.getTeacherClasses(teacherId);
        model.addAttribute("teacher", teacher);
        model.addAttribute("classes", classes);
        model.addAttribute("analytics", analytics);
        model.addAttribute("classId", classId);
        return "teacher/analytics";
    }

    @GetMapping("/students")
    public String students(Model model, HttpSession session) {
        if (!checkTeacherRole(session)) {
            return "redirect:/login";
        }
        Integer teacherId = (Integer) session.getAttribute("userId");
        Teacher teacher = teacherService.getTeacherById(teacherId);
        List<SchoolClass> classes = teacherService.getTeacherClasses(teacherId);
        model.addAttribute("teacher", teacher);
        model.addAttribute("classes", classes);
        return "teacher/students";
    }

    @PostMapping("/students")
    public String studentsForClass(@RequestParam("classId") Integer classId, Model model, HttpSession session) {
        if (!checkTeacherRole(session)) {
            return "redirect:/login";
        }
        Integer teacherId = (Integer) session.getAttribute("userId");
        Teacher teacher = teacherService.getTeacherById(teacherId);
        List<Student> students = teacherService.getClassStudentsWithDetails(classId, teacherId);
        List<SchoolClass> classes = teacherService.getTeacherClasses(teacherId);
        model.addAttribute("teacher", teacher);
        model.addAttribute("classes", classes);
        model.addAttribute("students", students);
        model.addAttribute("classId", classId);
        return "teacher/students";
    }

    private Teacher getTeacherById(Integer teacherId) {
        return teacherService.getTeacherById(teacherId);
    }
}