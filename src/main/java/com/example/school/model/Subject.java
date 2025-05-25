package com.example.school.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "subject")
public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long subjectId;

    private String subjectName;
    private String description;
    private Integer hoursPerWeek;
    private Boolean isMandatory;

    @OneToMany(mappedBy = "subject")
    private Set<Teaching> teachings;

    @OneToMany(mappedBy = "subject")
    private Set<Grade> grades;

    @OneToMany(mappedBy = "subject")
    private Set<Schedule> schedules;

    @OneToMany(mappedBy = "subject")
    private Set<LearningMaterial> materials;
}
