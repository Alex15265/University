package com.foxminded.university.repositories;

import com.foxminded.university.entities.LessonTime;
import org.springframework.data.repository.CrudRepository;

public interface LessonTimeRepository extends CrudRepository<LessonTime, Integer> {
}
