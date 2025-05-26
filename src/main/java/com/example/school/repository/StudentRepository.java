package com.example.school.repository;

import com.example.school.model.SchoolClass;
import com.example.school.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {
    Optional<Student> findByLogin(String login);

    List<Student> findByClassId(SchoolClass classId);
}
