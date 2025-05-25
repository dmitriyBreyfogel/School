package com.example.school.repository;

import com.example.school.model.LearningMaterial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LearningMaterialRepository extends JpaRepository<LearningMaterial, Long> {
    List<LearningMaterial> findByIsAvailableForStudents(Boolean isAvailableForStudents);
}
