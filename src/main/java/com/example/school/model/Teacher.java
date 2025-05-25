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
    private long teacherId;

    private String lastName;
    private String firstName;
    private String middleName;
    private LocalDate birthDate;
    private String address;
    private String phone;
    private String education;
    private Integer experience;
    private String position;

    @Column(unique = true)
    private String login;

    private String passwordHash;
    private LocalDateTime lastLogin;
    private String role = "teacher";

    @OneToMany(mappedBy = "teacher")
    private Set<Teaching> teachings;

    @OneToMany(mappedBy = "classTeacher")
    private Set<SchoolClass> classes;

    @OneToMany(mappedBy = "responsibleTeacher")
    private Set<SchoolEvent> events;

    @OneToMany(mappedBy = "teacher")
    private Set<LearningMaterial> materials;

    @OneToMany(mappedBy = "teacher")
    private Set<Schedule> schedules;
}
