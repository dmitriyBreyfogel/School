package com.example.school.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubjectDTO {
    private Integer subjectId;
    private String subjectName;
    private String description;
    private Integer hoursPerWeek;
    private Boolean isMandatory;
}