package com.foxminded.university.repositories;

import com.foxminded.university.entities.Course;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CourseRepository extends CrudRepository<Course, Integer> {
    List<Course> findByOrderByCourseIdAsc();
    Course findByCourseName(String courseName);
}
