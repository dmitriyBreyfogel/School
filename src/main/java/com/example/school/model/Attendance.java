package com.example.school.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "attendance")
public class Attendance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer attendanceId;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student studentId;

    private LocalDate attendanceDate;

    @ManyToOne
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;

    private Boolean isPresent;
    private String absenceReason;
}
