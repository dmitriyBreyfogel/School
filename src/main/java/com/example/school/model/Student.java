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
    private Long studentId;

    private String lastName;
    private String firstName;
    private String middleName;
    private LocalDate birthDate;
    private String address;
    private String phone;
    private LocalDate enrollmentDate;
    private String gender;

    @Column(unique = true)
    private String login;

    private String passwordHash;
    private LocalDateTime lastLogin;
    private String role = "student";

    @ManyToOne
    @JoinColumn(name = "class_id")
    private SchoolClass studentClass;

    @OneToMany(mappedBy = "student")
    private Set<Grade> grades;

    @OneToMany(mappedBy = "student")
    private Set<Attendance> attendances;

    @ManyToMany
    @JoinTable(
            name = "parent_student",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "parent_id")
    )
    private Set<Parent> parents;
}