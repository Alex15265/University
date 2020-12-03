package com.foxminded.university.repositories;

import com.foxminded.university.entities.Lesson;
import com.foxminded.university.entities.LessonTime;
import com.foxminded.university.entities.Professor;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface LessonRepository extends CrudRepository<Lesson, Integer> {
    List<Lesson> findByOrderByLessonIdAsc();
    Lesson findByProfessorAndTime(Professor professor, LessonTime lessonTime);
}
