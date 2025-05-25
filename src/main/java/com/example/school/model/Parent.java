package com.example.school.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "parent")
public class Parent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer parentId;

    @OneToMany(mappedBy = "parentId")
    private Set<Parent_Student> parentStudents;

    private String lastName;
    private String firstName;
    private String middleName;
    private String phone;
    private String address;
    private String workplace;
    private String email;
    private String status;
    private String login;
    private String passwordHash;
    private LocalDateTime lastLogin;
    private String role;
}