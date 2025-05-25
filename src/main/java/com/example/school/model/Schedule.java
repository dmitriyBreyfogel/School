package com.example.school.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Time;

@Getter
@Setter
@Entity
@Table(name = "schedule")
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer scheduleId;

    private Integer dayOfWeek;
    private Integer lessonNumber;
    private Time startTime;
    private Time endTime;

    @ManyToOne
    @JoinColumn(name = "subject_id")
    private Subject subjectId;

    @ManyToOne
    @JoinColumn(name = "class_id")
    private SchoolClass classId;

    @ManyToOne
    @JoinColumn(name = "teacher_id")
    private Teacher teacherId;

    @ManyToOne
    @JoinColumn(name = "classroom_id")
    private Classroom classroomId;
}