package com.example.school.dto;

import java.time.LocalDate;

public class AttendanceDTO {
    private Integer attendanceId;
    private Integer studentId;
    private String firstName;
    private String lastName;
    private LocalDate attendanceDate;
    private Integer schedule; // Предполагаем, что schedule представлен как Integer (ID), уточните, если иначе
    private Boolean isPresent;
    private String absenceReason;

    // Конструктор по умолчанию (для других случаев, если нужен)
    public AttendanceDTO() {
    }

    // Конструктор, соответствующий JPQL-запросу
    public AttendanceDTO(Integer attendanceId, Integer studentId, String firstName, String lastName,
                         LocalDate attendanceDate, Integer schedule, Boolean isPresent, String absenceReason) {
        this.attendanceId = attendanceId;
        this.studentId = studentId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.attendanceDate = attendanceDate;
        this.schedule = schedule;
        this.isPresent = isPresent;
        this.absenceReason = absenceReason;
    }

    // Геттеры и сеттеры
    public Integer getAttendanceId() { return attendanceId; }
    public void setAttendanceId(Integer attendanceId) { this.attendanceId = attendanceId; }

    public Integer getStudentId() { return studentId; }
    public void setStudentId(Integer studentId) { this.studentId = studentId; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public LocalDate getAttendanceDate() { return attendanceDate; }
    public void setAttendanceDate(LocalDate attendanceDate) { this.attendanceDate = attendanceDate; }

    public Integer getSchedule() { return schedule; }
    public void setSchedule(Integer schedule) { this.schedule = schedule; }

    public Boolean getIsPresent() { return isPresent; }
    public void setIsPresent(Boolean isPresent) { this.isPresent = isPresent; }

    public String getAbsenceReason() { return absenceReason; }
    public void setAbsenceReason(String absenceReason) { this.absenceReason = absenceReason; }
}