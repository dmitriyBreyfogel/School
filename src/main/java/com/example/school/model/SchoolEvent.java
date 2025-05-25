package com.example.school.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Time;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "school_event")
public class SchoolEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer eventId;

    private String eventName;
    private LocalDate eventDate;
    private Time startTime;
    private Time endTime;
    private String description;
    private String location;

    @ManyToOne
    @JoinColumn(name = "responsible_teacher_id")
    private Teacher teacherId;
}