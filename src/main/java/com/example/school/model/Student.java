package com.example.school.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "student")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer studentId;

    private String lastName;
    private String firstName;
    private String middleName;
    private LocalDate birthDate;
    private String address;
    private String phone;

    @OneToMany(mappedBy = "studentId")
    private Set<Parent_Student> parentStudents;

    @ManyToOne
    @JoinColumn(name = "class_id")
    private SchoolClass classId;

    private LocalDate enrollmentDate;
    private char gender;
    private String login;
    private String passwordHash;
    private LocalDateTime lastLogin;
    private String role;
}