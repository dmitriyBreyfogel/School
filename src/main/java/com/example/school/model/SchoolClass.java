package com.example.school.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "class")
public class SchoolClass {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "class_id")
    private Integer classId;

    @OneToMany(mappedBy = "classId")
    private Set<Schedule> schedules;

    @OneToMany(mappedBy = "classId")
    private Set<Student> students;

    private Integer classNumber;
    private String classLetter;

    @ManyToOne
    @JoinColumn(name = "class_teacher_id")
    private Teacher teacherId;

    private Integer formationYear;
    private String profile;
}