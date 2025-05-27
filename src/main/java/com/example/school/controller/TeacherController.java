package com.example.school.controller;

import com.example.school.dto.AttendanceDTO;
import com.example.school.dto.GradeDTO;
import com.example.school.dto.ScheduleDTO;
import com.example.school.dto.StudentAnalyticsDTO;
import com.example.school.model.*;
import com.example.school.repository.GradeRepository;
import com.example.school.service.AnalyticsService;
import com.example.school.service.TeacherService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/teacher")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private AnalyticsService analyticsService;
    @Autowired
    private LocalContainerEntityManagerFactoryBean entityManagerFactory2;
    @Autowired
    private GradeRepository gradeRepository;

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
        model.addAttribute("students", new ArrayList<Student>());
        model.addAttribute("subjects", teacherService.getTeacherSubjects(teacherId));
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
        List<Student> students = teacherService.getClassStudentsWithDetails(classId, teacherId);
        model.addAttribute("teacher", teacher);
        model.addAttribute("classes", classes);
        model.addAttribute("grades", grades);
        model.addAttribute("classId", classId);
        model.addAttribute("students", students);
        model.addAttribute("subjects", teacherService.getTeacherSubjects(teacherId));
        return "teacher/grades";
    }

    @PostMapping("/add-grade")
    public String addGrade(@ModelAttribute GradeDTO gradeDTO, @RequestParam("classId") Integer classId, HttpSession session, Model model) {
        if (!checkTeacherRole(session)) {
            return "redirect:/login";
        }
        Integer teacherId = (Integer) session.getAttribute("userId");
        try {
            teacherService.addGrade(gradeDTO, teacherId);
        } catch (Exception e) {
            model.addAttribute("error", "Ошибка при добавлении оценки: " + e.getMessage());
            return null;
        }
        return "redirect:/teacher/grades?classId=" + classId;
    }

    @GetMapping("/update-grade")
    public String showUpdateGradeForm(@RequestParam("gradeId") Integer gradeId, @RequestParam("classId") Integer classId,
                                      HttpSession session, Model model) {
        if (!checkTeacherRole(session)) {
            return "redirect:/login";
        }
        Integer teacherId = (Integer) session.getAttribute("userId");
        Optional<Grade> gradeOpt = gradeRepository.findById(gradeId);
        if (gradeOpt.isEmpty()) {
            model.addAttribute("error", "Оценка не найдена");
            return null;
        }
        Grade grade = gradeOpt.get();
        GradeDTO gradeDTO = new GradeDTO();
        gradeDTO.setGradeId(grade.getGradeId());
        gradeDTO.setStudentId(grade.getStudentId().getStudentId());
        gradeDTO.setSubjectId(grade.getSubjectId().getSubjectId());
        gradeDTO.setGradeDate(grade.getGradeDate());
        gradeDTO.setGradeValue(grade.getGradeValue());
        gradeDTO.setGradeType(grade.getGradeType());
        gradeDTO.setComment(grade.getComment());
        model.addAttribute("gradeDTO", gradeDTO);
        return null;
    }

    @PostMapping("/update-grade")
    public String updateGrade(@ModelAttribute GradeDTO gradeDTO, @RequestParam("classId") Integer classId, HttpSession session, Model model) {
        if (!checkTeacherRole(session)) {
            return "redirect:/login";
        }
        Integer teacherId = (Integer) session.getAttribute("userId");
        try {
            teacherService.updateGrade(gradeDTO, teacherId);
        } catch (Exception e) {
            model.addAttribute("error", "Ошибка при обновлении оценки: " + e.getMessage());
            return null;
        }
        return "redirect:/teacher/grades?classId=" + classId;
    }

    @PostMapping("/delete-grade")
    public String deleteGrade(@RequestParam("gradeId") Integer gradeId, @RequestParam("classId") Integer classId, HttpSession session, Model model) {
        if (!checkTeacherRole(session)) {
            return "redirect:/login";
        }
        Integer teacherId = (Integer) session.getAttribute("userId");
        try {
            teacherService.deleteGrade(gradeId, teacherId);
        } catch (Exception e) {
            model.addAttribute("error", "Ошибка при удалении оценки: " + e.getMessage());
            return null;
        }
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