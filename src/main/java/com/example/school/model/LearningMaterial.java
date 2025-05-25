package com.example.school.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "learning_material")
public class LearningMaterial {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer materialId;

    private String title;
    private String materialType;
    private LocalDate creationDate;

    @ManyToOne
    @JoinColumn(name = "subject_id")
    private Subject subjectId;

    @ManyToOne
    @JoinColumn(name = "teacher_id")
    private Teacher teacherId;

    private String description;
    private String filePath;
    private Boolean isAvailableForStudents;
}