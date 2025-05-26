package com.example.school.service;

import com.example.school.dto.StudentAnalyticsDTO;
import com.example.school.model.Grade;
import com.example.school.model.Attendance;
import com.example.school.model.SchoolClass;
import com.example.school.model.Student;
import com.example.school.repository.GradeRepository;
import com.example.school.repository.AttendanceRepository;
import com.example.school.repository.StudentRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AnalyticsService {

    private final GradeRepository gradeRepository;
    private final AttendanceRepository attendanceRepository;
    private final StudentRepository studentRepository;
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public AnalyticsService(GradeRepository gradeRepository,
                            AttendanceRepository attendanceRepository,
                            StudentRepository studentRepository) {
        this.gradeRepository = gradeRepository;
        this.attendanceRepository = attendanceRepository;
        this.studentRepository = studentRepository;
    }

    // Класс для хранения аналитических данных по каждому ученику
    public static class StudentAnalytics {
        private Integer studentId;
        private String firstName;
        private String lastName;
        private Double averageGrade;
        private Integer gradeCount;
        private Integer attendanceCount;
        private Integer presentCount;

        // Конструктор
        public StudentAnalytics(Integer studentId, String firstName, String lastName) {
            this.studentId = studentId;
            this.firstName = firstName;
            this.lastName = lastName;
            this.averageGrade = null;
            this.gradeCount = 0;
            this.attendanceCount = 0;
            this.presentCount = 0;
        }

        public Integer getStudentId() { return studentId; }
        public String getFirstName() { return firstName; }
        public String getLastName() { return lastName; }
        public Double getAverageGrade() { return averageGrade; }
        public void setAverageGrade(Double averageGrade) { this.averageGrade = averageGrade; }
        public Integer getGradeCount() { return gradeCount; }
        public void setGradeCount(Integer gradeCount) { this.gradeCount = gradeCount; }
        public Integer getAttendanceCount() { return attendanceCount; }
        public void setAttendanceCount(Integer attendanceCount) { this.attendanceCount = attendanceCount; }
        public Integer getPresentCount() { return presentCount; }
        public void setPresentCount(Integer presentCount) { this.presentCount = presentCount; }
    }

    // Метод для получения аналитики по классу
    public List<StudentAnalytics> getAnalyticsByClass(SchoolClass schoolClass) {
        // Получаем всех учеников класса
        List<Student> students = studentRepository.findByClassId(schoolClass);
        List<StudentAnalytics> analyticsList = new ArrayList<>();

        for (Student student : students) {
            StudentAnalytics analytics = new StudentAnalytics(
                    student.getStudentId(),
                    student.getFirstName(),
                    student.getLastName()
            );

            // Получаем оценки ученика
            List<Grade> grades = gradeRepository.findByStudentId(student);
            if (!grades.isEmpty()) {
                double average = grades.stream()
                        .mapToInt(Grade::getGradeValue)
                        .average()
                        .orElse(0.0);
                analytics.setAverageGrade(average);
                analytics.setGradeCount(grades.size());
            }

            // Получаем данные о посещаемости
            List<Attendance> attendances = attendanceRepository.findByStudentId(student);
            if (!attendances.isEmpty()) {
                int presentCount = (int) attendances.stream()
                        .filter(Attendance::getIsPresent)
                        .count();
                analytics.setAttendanceCount(attendances.size());
                analytics.setPresentCount(presentCount);
            }

            analyticsList.add(analytics);
        }

        return analyticsList;
    }

    // Новый метод для получения аналитики по classId
    public List<StudentAnalyticsDTO> getStudentPerformanceByClass(Integer classId) {
        // Находим SchoolClass по classId
        SchoolClass schoolClass = entityManager.find(SchoolClass.class, classId);
        if (schoolClass == null) {
            return new ArrayList<>(); // Возвращаем пустой список, если класс не найден
        }

        // Получаем аналитику для класса
        List<StudentAnalytics> analyticsList = getAnalyticsByClass(schoolClass);

        // Преобразуем StudentAnalytics в StudentAnalyticsDTO
        List<StudentAnalyticsDTO> dtoList = new ArrayList<>();
        for (StudentAnalytics analytics : analyticsList) {
            StudentAnalyticsDTO dto = new StudentAnalyticsDTO(
                    analytics.getStudentId(),
                    analytics.getFirstName(),
                    analytics.getLastName(),
                    analytics.getAverageGrade(),
                    analytics.getGradeCount(),
                    analytics.getAttendanceCount(),
                    analytics.getPresentCount()
            );
            dtoList.add(dto);
        }

        return dtoList;
    }
}