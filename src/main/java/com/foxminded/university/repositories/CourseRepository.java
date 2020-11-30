package com.foxminded.university.repositories;

import com.foxminded.university.entities.Course;
import org.springframework.data.repository.CrudRepository;

public interface CourseRepository extends CrudRepository<Course, Integer> {
}
