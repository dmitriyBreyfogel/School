package com.example.school.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClassroomDTO {
    private Integer classroomId;
    private String roomNumber;
    private String roomName;
    private Integer capacity;
    private String description;
    private Integer floor;
    private String equipment;
}