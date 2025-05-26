package com.example.school.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SchoolEventDTO {
    private Integer eventId;
    private String eventName;
    private String eventDate;
    private String startTime;
    private String endTime;
    private String description;
    private String location;
    private Integer responsibleTeacherId;
    private String responsibleTeacherName;
}