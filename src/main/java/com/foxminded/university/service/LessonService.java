package com.foxminded.university.service;

import com.foxminded.university.dao.LessonDAO;
import com.foxminded.university.dao.entities.Lesson;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;

import java.rmi.NoSuchObjectException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class LessonService {
    private final LessonDAO lessonDAO;
    private final ProfessorService professorService;
    private final CourseService courseService;
    private final ClassRoomService classRoomService;
    private final LessonTimeService lessonTimeService;

    public Lesson create(Integer professorId, Integer courseId, Integer roomId, Integer timeId) throws NoSuchObjectException {
        Lesson lesson = new Lesson();
        lesson.setProfessor(professorService.readById(professorId));
        lesson.setCourse(courseService.readByID(courseId));
        lesson.setClassRoom(classRoomService.readByID(roomId));
        lesson.setTime(lessonTimeService.readByID(timeId));
        return lessonDAO.create(lesson);
    }

    public List<Lesson> readAll() {
        return lessonDAO.readAll();
    }

    public Lesson readById(Integer lessonId) throws NoSuchObjectException {
        try {
        return lessonDAO.readByID(lessonId);
        } catch (EmptyResultDataAccessException e) {
            throw new NoSuchObjectException("Object not found");
        }
    }

    public Lesson update(Integer lessonId, Integer professorId, Integer courseId, Integer roomId, Integer timeId) throws NoSuchObjectException, NoSuchObjectException {
            Lesson lesson = new Lesson();
            lesson.setLessonId(lessonId);
            lesson.setProfessor(professorService.readById(professorId));
            lesson.setCourse(courseService.readByID(courseId));
            lesson.setClassRoom(classRoomService.readByID(roomId));
            lesson.setTime(lessonTimeService.readByID(timeId));
            return lessonDAO.update(lesson);
    }

    public void delete(Integer lessonId) {
        lessonDAO.delete(lessonId);
    }

    public void addGroupToLesson(Integer groupId, Integer lessonId) {
        lessonDAO.addGroupToLesson(groupId, lessonId);
    }

    public void deleteGroupFromLesson(Integer groupId, Integer lessonId) {
        lessonDAO.deleteGroupFromLesson(groupId, lessonId);
    }
}
