package com.example.school.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "parent_student")
public class Parent_Student {
    @EmbeddedId
    private Parent_Student_Id id;

    @ManyToOne
    @JoinColumn(name = "parent_id", insertable = false, updatable = false)
    private Parent parentId;

    @ManyToOne
    @JoinColumn(name = "student_id", insertable = false, updatable = false)
    private Student studentId;
}
