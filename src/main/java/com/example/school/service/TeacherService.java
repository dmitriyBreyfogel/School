package com.example.school.service;

import com.example.school.dto.AttendanceDTO;
import com.example.school.dto.GradeDTO;
import com.example.school.dto.ScheduleDTO;
import com.example.school.dto.StudentAnalyticsDTO;
import com.example.school.model.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TeacherService {

    @PersistenceContext
    private EntityManager entityManager;

    public Teacher getTeacherById(Integer teacherId) {
        return entityManager.find(Teacher.class, teacherId);
    }

    public List<SchoolClass> getTeacherClasses(Integer teacherId) {
        Query query = entityManager.createQuery("SELECT c FROM SchoolClass c WHERE c.teacherId.teacherId = :teacherId");
        query.setParameter("teacherId", teacherId);
        return query.getResultList();
    }

    public List<List<Student>> getAllStudentsForTeacherClasses(Integer teacherId) {
        List<SchoolClass> classes = getTeacherClasses(teacherId);
        List<List<Student>> allStudents = new ArrayList<>();
        for (SchoolClass schoolClass : classes) {
            Query query = entityManager.createQuery(
                    "SELECT s FROM Student s WHERE s.classId.classId = :classId");
            query.setParameter("classId", schoolClass.getClassId());
            List<Student> students = query.getResultList();
            allStudents.add(students);
        }
        return allStudents;
    }

    public List<Subject> getTeacherSubjects(Integer teacherId) {
        Query query = entityManager.createQuery(
                "SELECT s FROM Subject s JOIN Teaching t ON s.subjectId = t.subjectId.subjectId WHERE t.teacherId.teacherId = :teacherId");
        query.setParameter("teacherId", teacherId);
        return query.getResultList();
    }

    public List<ScheduleDTO> getTodaySchedule(Integer teacherId) {
        LocalDate today = LocalDate.now();
        Query query = entityManager.createQuery(
                "SELECT new com.example.school.dto.ScheduleDTO(s.scheduleId, s.dayOfWeek, s.lessonNumber, " +
                        "s.startTime, s.endTime, sub.subjectName, c.classNumber, c.classLetter, cr.roomNumber) " +
                        "FROM Schedule s JOIN Subject sub ON s.subjectId.subjectId = sub.subjectId " +
                        "JOIN SchoolClass c ON s.classId.classId = c.classId " +
                        "JOIN Classroom cr ON s.classroomId.classroomId = cr.classroomId " +
                        "WHERE s.teacherId.teacherId = :teacherId AND s.dayOfWeek = :dayOfWeek");
        query.setParameter("teacherId", teacherId);
        query.setParameter("dayOfWeek", today.getDayOfWeek().getValue());
        return query.getResultList();
    }

    public List<ScheduleDTO> getWeekSchedule(Integer teacherId) {
        Query query = entityManager.createQuery(
                "SELECT new com.example.school.dto.ScheduleDTO(s.scheduleId, s.dayOfWeek, s.lessonNumber, " +
                        "s.startTime, s.endTime, sub.subjectName, c.classNumber, c.classLetter, cr.roomNumber) " +
                        "FROM Schedule s JOIN Subject sub ON s.subjectId.subjectId = sub.subjectId " +
                        "JOIN SchoolClass c ON s.classId.classId = c.classId " +
                        "JOIN Classroom cr ON s.classroomId.classroomId = cr.classroomId " +
                        "WHERE s.teacherId.teacherId = :teacherId");
        query.setParameter("teacherId", teacherId);
        return query.getResultList();
    }

    public List<GradeDTO> getClassGrades(Integer classId, Integer teacherId) {
        Query query = entityManager.createQuery(
                "SELECT new com.example.school.dto.GradeDTO(g.gradeId, g.studentId.studentId, st.firstName, st.lastName, " +
                        "g.subjectId.subjectId, sub.subjectName, g.gradeDate, g.gradeValue, g.gradeType, g.comment) " +
                        "FROM Grade g JOIN Student st ON g.studentId.studentId = st.studentId " +
                        "JOIN Subject sub ON g.subjectId.subjectId = sub.subjectId " +
                        "JOIN Teaching t ON sub.subjectId = t.subjectId.subjectId " +
                        "WHERE st.classId.classId = :classId AND t.teacherId.teacherId = :teacherId");
        query.setParameter("classId", classId);
        query.setParameter("teacherId", teacherId);
        return query.getResultList();
    }

    @Transactional
    public void addGrade(GradeDTO gradeDTO, Integer teacherId) {
        Grade grade = new Grade();
        grade.setStudentId(entityManager.find(Student.class, gradeDTO.getStudentId()));
        grade.setSubjectId(entityManager.find(Subject.class, gradeDTO.getSubjectId()));
        grade.setGradeDate(gradeDTO.getGradeDate());
        grade.setGradeValue(gradeDTO.getGradeValue());
        grade.setGradeType(gradeDTO.getGradeType());
        grade.setComment(gradeDTO.getComment());
        entityManager.persist(grade);
    }

    @Transactional
    public void updateGrade(GradeDTO gradeDTO, Integer teacherId) {
        Grade grade = entityManager.find(Grade.class, gradeDTO.getGradeId());
        if (grade != null && isTeacherAuthorizedForGrade(grade, teacherId)) {
            grade.setGradeDate(gradeDTO.getGradeDate());
            grade.setGradeValue(gradeDTO.getGradeValue());
            grade.setGradeType(gradeDTO.getGradeType());
            grade.setComment(gradeDTO.getComment());
            entityManager.merge(grade);
        }
    }

    @Transactional
    public void deleteGrade(Integer gradeId, Integer teacherId) {
        Grade grade = entityManager.find(Grade.class, gradeId);
        if (grade != null && isTeacherAuthorizedForGrade(grade, teacherId)) {
            entityManager.remove(grade);
        } else {
            throw new IllegalArgumentException("Grade not found or teacher not authorized");
        }
    }

    private boolean isTeacherAuthorizedForGrade(Grade grade, Integer teacherId) {
        Query query = entityManager.createQuery(
                "SELECT COUNT(t) FROM Teaching t WHERE t.teacherId.teacherId = :teacherId AND t.subjectId.subjectId = :subjectId");
        query.setParameter("teacherId", teacherId);
        query.setParameter("subjectId", grade.getSubjectId().getSubjectId());
        return ((Long) query.getSingleResult()) > 0;
    }

    public List<AttendanceDTO> getClassAttendance(Integer classId, String date, Integer teacherId) {
        Query query = entityManager.createQuery(
                "SELECT new com.example.school.dto.AttendanceDTO(a.attendanceId, a.studentId.studentId, st.firstName, st.lastName, " +
                        "a.attendanceDate, a.schedule.scheduleId, a.isPresent, a.absenceReason) " +
                        "FROM Attendance a JOIN Student st ON a.studentId.studentId = st.studentId " +
                        "JOIN Schedule s ON a.schedule.scheduleId = s.scheduleId " +
                        "WHERE st.classId.classId = :classId AND a.attendanceDate = :date AND s.teacherId.teacherId = :teacherId");
        query.setParameter("classId", classId);
        query.setParameter("date", LocalDate.parse(date));
        query.setParameter("teacherId", teacherId);
        return query.getResultList();
    }

    @Transactional
    public void markAttendance(List<AttendanceDTO> attendanceList, Integer teacherId) {
        for (AttendanceDTO dto : attendanceList) {
            Attendance attendance = entityManager.find(Attendance.class, dto.getAttendanceId());
            if (attendance == null) {
                attendance = new Attendance();
                attendance.setStudentId(entityManager.find(Student.class, dto.getStudentId()));
                attendance.setSchedule(entityManager.find(Schedule.class, dto.getSchedule()));
                attendance.setAttendanceDate(dto.getAttendanceDate());
            }
            attendance.setIsPresent(dto.getIsPresent());
            attendance.setAbsenceReason(dto.getAbsenceReason());
            entityManager.persist(attendance);
        }
    }

    @Transactional
    public void updateAttendance(AttendanceDTO attendanceDTO, Integer teacherId) {
        Attendance attendance = entityManager.find(Attendance.class, attendanceDTO.getAttendanceId());
        if (attendance != null && isTeacherAuthorizedForAttendance(attendance, teacherId)) {
            attendance.setIsPresent(attendanceDTO.getIsPresent());
            attendance.setAbsenceReason(attendanceDTO.getAbsenceReason());
            entityManager.merge(attendance);
        }
    }

    @Transactional
    public void deleteAttendance(Integer attendanceId, Integer teacherId) {
        Attendance attendance = entityManager.find(Attendance.class, attendanceId);
        if (attendance != null && isTeacherAuthorizedForAttendance(attendance, teacherId)) {
            entityManager.remove(attendance);
        }
    }

    private boolean isTeacherAuthorizedForAttendance(Attendance attendance, Integer teacherId) {
        Query query = entityManager.createQuery(
                "SELECT COUNT(s) FROM Schedule s WHERE s.teacherId.teacherId = :teacherId AND s.scheduleId = :scheduleId");
        query.setParameter("teacherId", teacherId);
        query.setParameter("scheduleId", attendance.getSchedule().getScheduleId());
        return ((Long) query.getSingleResult()) > 0;
    }

    public List<LearningMaterial> getTeacherMaterials(Integer teacherId) {
        Query query = entityManager.createQuery(
                "SELECT m FROM LearningMaterial m WHERE m.teacherId.teacherId = :teacherId");
        query.setParameter("teacherId", teacherId);
        return query.getResultList();
    }

    public List<Student> getClassStudentsWithDetails(Integer classId, Integer teacherId) {
        Query query = entityManager.createQuery(
                "SELECT s FROM Student s JOIN s.classId c WHERE c.classId = :classId AND c.teacherId.teacherId = :teacherId");
        query.setParameter("classId", classId);
        query.setParameter("teacherId", teacherId);
        return query.getResultList();
    }
}