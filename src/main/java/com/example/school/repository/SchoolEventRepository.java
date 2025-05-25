package com.example.school.repository;

import com.example.school.model.SchoolEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SchoolEventRepository extends JpaRepository<SchoolEvent, Long> {
}
