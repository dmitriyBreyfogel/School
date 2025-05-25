package com.example.school.repository;

import com.example.school.model.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    List<Schedule> findBySchoolClassOrderByDayOfWeekAscLessonNumberAsc(Class schoolClass);

    List<Schedule> findByTeacher_TeacherIdOrderByDayOfWeekAscLessonNumberAsc(long teacherTeacherId);
}
