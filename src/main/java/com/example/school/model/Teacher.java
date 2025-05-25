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
@Table(name = "teacher")
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer teacherId;

    @OneToMany(mappedBy = "teacherId")
    private Set<SchoolClass> classesWhereMainTeacher;

    @OneToMany(mappedBy = "teacherId")
    private Set<SchoolEvent> schoolEvents;

    @OneToMany(mappedBy = "teacherId")
    private Set<Schedule> schedules;

    @OneToMany(mappedBy = "teacherId")
    private Set<LearningMaterial> materials;

    @OneToMany(mappedBy = "teacherId")
    private Set<Teaching> teachingSubjects;

    private String lastName;
    private String firstName;
    private String middleName;
    private LocalDate birthDate;
    private String address;
    private String phone;
    private String education;
    private Integer experience;
    private String position;
    private String login;
    private String passwordHash;
    private LocalDateTime lastLogin;
    private String role;
}