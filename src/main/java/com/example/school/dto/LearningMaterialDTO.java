package com.example.school.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LearningMaterialDTO {
    private Integer materialId;
    private String title;
    private String materialType;
    private String creationDate;
    private Integer subjectId;
    private String subjectName;
    private Integer teacherId;
    private String teacherName;
    private String description;
    private String filePath;
    private Boolean isAvailableForStudents;
}