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
    private Long classId;

    private Integer classNumber;
    private String classLetter;
    private Integer formationYear;
    private String profile;

    @ManyToOne
    @JoinColumn(name = "class_teacher_id")
    private Teacher classTeacher;

    @OneToMany(mappedBy = "studentClass")
    private Set<Student> students;

    @OneToMany(mappedBy = "class")
    private Set<Schedule> schedules;
}
