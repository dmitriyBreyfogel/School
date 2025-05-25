package com.example.school.service;

import com.example.school.model.*;
import com.example.school.repository.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserService {
    private final TeacherRepository teacherRepository;
    private final StudentRepository studentRepository;
    private final ParentRepository parentRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(TeacherRepository teacherRepository,
                       StudentRepository studentRepository,
                       ParentRepository parentRepository,
                       PasswordEncoder passwordEncoder) {
        this.teacherRepository = teacherRepository;
        this.studentRepository = studentRepository;
        this.parentRepository = parentRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Optional<Object> authenticate(String login, String password) {
        // Проверяем учителей
        Optional<Teacher> teacher = teacherRepository.findByLogin(login);
        if (teacher.isPresent() && passwordEncoder.matches(password, teacher.get().getPasswordHash())) {
            teacher.get().setLastLogin(LocalDateTime.now());
            teacherRepository.save(teacher.get());
            return Optional.of(teacher.get());
        }

        // Проверяем учеников
        Optional<Student> student = studentRepository.findByLogin(login);
        if (student.isPresent() && passwordEncoder.matches(password, student.get().getPasswordHash())) {
            student.get().setLastLogin(LocalDateTime.now());
            studentRepository.save(student.get());
            return Optional.of(student.get());
        }

        // Проверяем родителей
        Optional<Parent> parent = parentRepository.findByLogin(login);
        if (parent.isPresent() && passwordEncoder.matches(password, parent.get().getPasswordHash())) {
            parent.get().setLastLogin(LocalDateTime.now());
            parentRepository.save(parent.get());
            return Optional.of(parent.get());
        }

        return Optional.empty();
    }

    public boolean changePassword(Object user, String oldPassword, String newPassword) {
        if (user instanceof Teacher teacher) {
            if (passwordEncoder.matches(oldPassword, teacher.getPasswordHash())) {
                teacher.setPasswordHash(passwordEncoder.encode(newPassword));
                teacherRepository.save(teacher);
                return true;
            }
        } else if (user instanceof Student student) {
            if (passwordEncoder.matches(oldPassword, student.getPasswordHash())) {
                student.setPasswordHash(passwordEncoder.encode(newPassword));
                studentRepository.save(student);
                return true;
            }
        } else if (user instanceof Parent parent) {
            if (passwordEncoder.matches(oldPassword, parent.getPasswordHash())) {
                parent.setPasswordHash(passwordEncoder.encode(newPassword));
                parentRepository.save(parent);
                return true;
            }
        }
        return false;
    }
}
