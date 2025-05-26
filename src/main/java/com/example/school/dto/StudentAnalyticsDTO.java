package com.example.school.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentAnalyticsDTO {
    private Integer studentId;
    private String firstName;
    private String lastName;
    private Double averageGrade;
    private Integer gradeCount;
    private Integer presentCount;
    private Integer attendanceCount;

    public StudentAnalyticsDTO(Integer studentId, String firstName, String lastName, Double averageGrade,
                               Integer gradeCount, Integer presentCount, Integer attendanceCount) {
        this.studentId = studentId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.averageGrade = averageGrade;
        this.gradeCount = gradeCount;
        this.presentCount = presentCount;
        this.attendanceCount = attendanceCount;
    }
}