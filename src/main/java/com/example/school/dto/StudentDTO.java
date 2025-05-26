package com.example.school.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentDTO {
    private Integer studentId;
    private String lastName;
    private String firstName;
    private String middleName;
    private String birthDate;
    private String address;
    private String phone;
    private Integer classId;
    private String className;
    private String enrollmentDate;
    private String gender;
}