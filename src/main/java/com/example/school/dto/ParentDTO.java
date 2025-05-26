package com.example.school.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ParentDTO {
    private Integer parentId;
    private String lastName;
    private String firstName;
    private String middleName;
    private String phone;
    private String address;
    private String workplace;
    private String email;
    private String status;
}