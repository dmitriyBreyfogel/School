package com.example.school.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClassDTO {
    private Integer classId;
    private Integer classNumber;
    private String classLetter;
    private Integer classTeacherId;
    private String classTeacherName;
    private Integer formationYear;
    private String profile;
}