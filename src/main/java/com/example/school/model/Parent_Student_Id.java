package com.example.school.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Getter
@Setter
public class Parent_Student_Id implements Serializable {
    @Column(name = "parent_id")
    private int parentIdValue;

    @Column(name = "student_id")
    private int studentIdValue;

    // equals() и hashCode() обязательны для работы с Set
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Parent_Student_Id that = (Parent_Student_Id) o;
        return parentIdValue == that.parentIdValue && studentIdValue == that.studentIdValue;
    }

    @Override
    public int hashCode() {
        return Objects.hash(parentIdValue, studentIdValue);
    }
}