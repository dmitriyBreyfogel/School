package com.example.school.service;

import com.example.school.dto.AttendanceDTO;
import com.example.school.dto.GradeDTO;
import com.example.school.dto.ScheduleDTO;
import com.example.school.model.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ParentService {

    @PersistenceContext
    private EntityManager entityManager;

    public Parent getParentById(Integer parentId) {
        return entityManager.find(Parent.class, parentId);
    }

    public List<Student> getParentChildren(Integer parentId) {
        Query query = entityManager.createQuery(
                "SELECT s FROM Student s JOIN s.parentStudents ps JOIN ps.parentId p WHERE p.parentId = :parentId");
        query.setParameter("parentId", parentId);
        return query.getResultList();
    }

    public List<GradeDTO> getChildGrades(Integer studentId) {
        Query query = entityManager.createQuery(
                "SELECT new com.example.school.dto.GradeDTO(g.gradeId, g.studentId.studentId, s.firstName, s.lastName, g.subjectId.subjectId, sub.subjectName, g.gradeDate, g.gradeValue, g.gradeType, g.comment) " +
                        "FROM Grade g JOIN Student s ON g.subjectId.subjectId = s.studentId " +
                        "JOIN Subject sub ON g.subjectId.subjectId = sub.subjectId WHERE g.studentId = :studentId");
        query.setParameter("studentId", studentId);
        return query.getResultList();
    }

    public List<Object[]> getChildGradesBySubject(Integer studentId) {
        Query query = entityManager.createQuery(
                "SELECT sub.subjectName, AVG(g.gradeValue) FROM Grade g JOIN Subject sub ON g.subjectId.subjectId = sub.subjectId " +
                        "WHERE g.studentId = :studentId GROUP BY sub.subjectName");
        query.setParameter("studentId", studentId);
        return query.getResultList();
    }

    public List<AttendanceDTO> getChildAttendance(Integer studentId) {
        Query query = entityManager.createQuery(
                "SELECT new com.example.school.dto.AttendanceDTO(a.attendanceId, a.studentId.studentId, s.firstName, s.lastName, a.attendanceDate, a.schedule.id, a.isPresent, a.absenceReason)" +
                        "FROM Attendance a JOIN Student s ON a.studentId.studentId = s.studentId WHERE a.studentId = :studentId");
        query.setParameter("studentId", studentId);
        return query.getResultList();
    }

    public Object[] getChildAttendanceStats(Integer studentId) {
        Query query = entityManager.createQuery(
                "SELECT COUNT(a), SUM(CASE WHEN a.isPresent = true THEN 1 ELSE 0 END) FROM Attendance a WHERE a.studentId = :studentId");
        query.setParameter("studentId", studentId);
        return (Object[]) query.getSingleResult();
    }

    public List<Teacher> getChildTeachers(Integer studentId) {
        Query query = entityManager.createQuery(
                "SELECT DISTINCT t FROM Teacher t JOIN Schedule s ON t.teacherId = s.teacherId.teacherId " +
                        "JOIN Student st ON s.classId = st.classId WHERE st.studentId = :studentId");
        query.setParameter("studentId", studentId);
        return query.getResultList();
    }

    public List<SchoolEvent> getUpcomingEvents() {
        LocalDate today = LocalDate.now();
        Query query = entityManager.createQuery(
                "SELECT e FROM SchoolEvent e WHERE e.eventDate >= :today ORDER BY e.eventDate");
        query.setParameter("today", today);
        return query.getResultList();
    }

    public List<SchoolEvent> getPastEvents() {
        LocalDate today = LocalDate.now();
        Query query = entityManager.createQuery(
                "SELECT e FROM SchoolEvent e WHERE e.eventDate < :today ORDER BY e.eventDate DESC");
        query.setParameter("today", today);
        return query.getResultList();
    }

    public List<ScheduleDTO> getChildSchedule(Integer studentId) {
        Query query = entityManager.createQuery(
                "SELECT new com.example.school.dto.ScheduleDTO(s.scheduleId, s.dayOfWeek, s.lessonNumber, s.startTime, s.endTime, sub.subjectName, c.classNumber, c.classLetter, cr.roomNumber)" +
                        "FROM Schedule s JOIN Subject sub ON s.subjectId.subjectId = sub.subjectId " +
                        "JOIN SchoolClass c ON s.classId.classId = c.classId " +
                        "JOIN Classroom cr ON s.classroomId.classroomId = cr.classroomId " +
                        "JOIN Student st ON c.classId = st.classId.classId WHERE st.studentId = :studentId");
        query.setParameter("studentId", studentId);
        return query.getResultList();
    }
}