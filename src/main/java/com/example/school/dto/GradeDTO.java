package com.example.school.dto;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class GradeDTO {
    private Integer gradeId;
    private Integer studentId;
    private String studentFirstName;
    private String studentLastName;
    private Integer subjectId;
    private String subjectName;
    private LocalDate gradeDate;
    private Integer gradeValue;
    private String gradeType;
    private String comment;

    public GradeDTO(Integer gradeId, Integer studentId, String firstName, String lastName,
                    Integer subjectId, String subjectName, LocalDate gradeDate,
                    Integer gradeValue, String gradeType, String comment) {
        this.gradeId = gradeId;
        this.studentId = studentId;
        this.studentFirstName = firstName;
        this.studentLastName = lastName;
        this.subjectId = subjectId;
        this.subjectName = subjectName;
        this.gradeDate = gradeDate;
        this.gradeValue = gradeValue;
        this.gradeType = gradeType;
        this.comment = comment;
    }
}