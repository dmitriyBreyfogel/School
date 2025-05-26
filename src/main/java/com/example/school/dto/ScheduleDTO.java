package com.example.school.dto;

import lombok.Getter;
import lombok.Setter;

import java.sql.Time;
import java.time.LocalTime;

@Getter
@Setter
public class ScheduleDTO {
    private Integer scheduleId;
    private Integer dayOfWeek;
    private Integer lessonNumber;
    private Time startTime;
    private Time endTime;
    private String subjectName;
    private Integer classNumber;
    private String classLetter;
    private String roomNumber;

    public ScheduleDTO(Integer scheduleId, Integer dayOfWeek, Integer lessonNumber,
                       Time startTime, Time endTime, String subjectName,
                       Integer classNumber, String classLetter, String roomNumber) {
        this.scheduleId = scheduleId;
        this.dayOfWeek = dayOfWeek;
        this.lessonNumber = lessonNumber;
        this.startTime = startTime;
        this.endTime = endTime;
        this.subjectName = subjectName;
        this.classNumber = classNumber;
        this.classLetter = classLetter;
        this.roomNumber = roomNumber;
    }


}