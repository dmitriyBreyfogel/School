package com.example.school.service;

import com.example.school.model.*;
import com.example.school.repository.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DashboardService {
    private final GradeRepository gradeRepository;
    private final AttendanceRepository attendanceRepository;
    private final ScheduleRepository scheduleRepository;
    private final LearningMaterialRepository learningMaterialRepository;
    private final SchoolEventRepository schoolEventRepository;

    public DashboardService(GradeRepository gradeRepository,
                            AttendanceRepository attendanceRepository,
                            ScheduleRepository scheduleRepository,
                            LearningMaterialRepository learningMaterialRepository,
                            SchoolEventRepository schoolEventRepository) {
        this.gradeRepository = gradeRepository;
        this.attendanceRepository = attendanceRepository;
        this.scheduleRepository = scheduleRepository;
        this.learningMaterialRepository = learningMaterialRepository;
        this.schoolEventRepository = schoolEventRepository;
    }

    public List<Grade> getStudentGrades(Long studentId) {
        return gradeRepository.findByStudent_StudentId(studentId);
    }

    public List<Attendance> getStudentAttendances(Long studentId) {
        return attendanceRepository.findByStudent_StudentId(studentId);
    }

    public List<Schedule> getClassSchedule(Long classId) {
        return scheduleRepository.findBySchoolClassOrderByDayOfWeekAscLessonNumberAsc();
    }

    public List<LearningMaterial> getAvailableMaterialsForStudent(Long studentId) {
        // Получаем класс студента и предметы, которые он изучает
        // Затем получаем доступные материалы по этим предметам
        return learningMaterialRepository.findByIsAvailableForStudents();
    }

    public List<SchoolEvent> getUpcomingEvents() {
        return schoolEventRepository.;
    }

    public List<Grade> getClassGrades(Long classId) {
        return gradeRepository.findByStudent_StudentId(classId);
    }

    public List<Schedule> getTeacherSchedule(Long teacherId) {
        return scheduleRepository.findByTeacher_TeacherIdOrderByDayOfWeekAscLessonNumberAsc(teacherId);
    }
}
