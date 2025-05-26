package com.example.school.service;

import com.example.school.model.Parent;
import com.example.school.model.Student;
import com.example.school.model.Teacher;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @PersistenceContext
    private EntityManager entityManager;

    public AuthResult authenticate(String username, String password) {
        // Проверка учителя
        Query teacherQuery = entityManager.createQuery(
                "SELECT t FROM Teacher t WHERE t.phone = :username AND t.passwordHash = :password");
        teacherQuery.setParameter("username", username);
        teacherQuery.setParameter("password", password);
        try {
            Teacher teacher = (Teacher) teacherQuery.getSingleResult();
            return new AuthResult("teacher", teacher.getTeacherId());
        } catch (Exception e) {
            // Пользователь не найден среди учителей
        }

        // Проверка родителя
        Query parentQuery = entityManager.createQuery(
                "SELECT p FROM Parent p WHERE p.phone = :username AND p.passwordHash = :password");
        parentQuery.setParameter("username", username);
        parentQuery.setParameter("password", password);
        try {
            Parent parent = (Parent) parentQuery.getSingleResult();
            return new AuthResult("parent", parent.getParentId());
        } catch (Exception e) {
            // Пользователь не найден среди родителей
        }

        // Проверка ученика
        Query studentQuery = entityManager.createQuery(
                "SELECT s FROM Student s WHERE s.phone = :username AND s.passwordHash = :password");
        studentQuery.setParameter("username", username);
        studentQuery.setParameter("password", password);
        try {
            Student student = (Student) studentQuery.getSingleResult();
            return new AuthResult("student", student.getStudentId());
        } catch (Exception e) {
            // Пользователь не найден среди учеников
        }

        return null;
    }

    public static class AuthResult {
        private String role;
        private Integer userId;

        public AuthResult(String role, Integer userId) {
            this.role = role;
            this.userId = userId;
        }

        public String getRole() {
            return role;
        }

        public Integer getUserId() {
            return userId;
        }
    }
}