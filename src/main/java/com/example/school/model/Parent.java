package com.example.school.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "parent")
public class Parent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long parentId;

    private String lastName;
    private String firstName;
    private String middleName;
    private String phone;
    private String address;
    private String workplace;
    private String email;
    private String status;

    @Column(unique = true)
    private String login;

    private String passwordHash;
    private LocalDateTime lastLogin;
    private String role = "parent";

    @ManyToMany(mappedBy = "parents")
    private Set<Student> children;

    @ManyToMany(mappedBy = "parents")
    private List<Student> listChildren;
}
