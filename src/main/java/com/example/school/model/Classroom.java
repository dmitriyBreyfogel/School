package com.example.school.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "classroom")
public class Classroom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long classroomId;

    private String roomNumber;
    private String roomName;
    private Integer capacity;
    private String description;
    private Integer floor;
    private String equipment;

    @OneToMany(mappedBy = "classroom")
    private Set<Schedule> schedules;
}
