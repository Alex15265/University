package com.foxminded.university.repositories;

import com.foxminded.university.entities.LessonTime;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface LessonTimeRepository extends CrudRepository<LessonTime, Integer> {
    List<LessonTime> findByOrderByTimeIdAsc();
    LessonTime findByLessonStart(LocalDateTime lessonStart);
}
