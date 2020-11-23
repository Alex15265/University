package com.foxminded.university.service;

import com.foxminded.university.dao.LessonDAO;
import com.foxminded.university.dao.entities.Lesson;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.rmi.NoSuchObjectException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LessonService {
    private final LessonDAO lessonDAO;
    private final ProfessorService professorService;
    private final CourseService courseService;
    private final ClassRoomService classRoomService;
    private final LessonTimeService lessonTimeService;
    private final Logger logger = LoggerFactory.getLogger(LessonService.class);

    public Lesson create(Integer professorId, Integer courseId, Integer roomId, Integer timeId) throws NoSuchObjectException {
        logger.debug("creating lesson with professorId: {}, courseId: {}, roomId: {}, timeId: {}",
                professorId, courseId, roomId, timeId);
        Lesson lesson = new Lesson();
        lesson.setProfessor(professorService.readById(professorId));
        lesson.setCourse(courseService.readByID(courseId));
        lesson.setClassRoom(classRoomService.readByID(roomId));
        lesson.setTime(lessonTimeService.readByID(timeId));
        return lessonDAO.create(lesson);
    }

    public List<Lesson> readAll() {
        logger.debug("reading all lessons");
        return lessonDAO.readAll();
    }

    public Lesson readById(Integer lessonId) throws NoSuchObjectException {
        logger.debug("reading lesson with ID: {}", lessonId);
        try {
        return lessonDAO.readByID(lessonId);
        } catch (EmptyResultDataAccessException e) {
            logger.warn("reading lesson with ID: {} exception: {}", lessonId, e.getMessage());
            throw new NoSuchObjectException("Object not found");
        }
    }

    public Lesson update(Integer lessonId, Integer professorId, Integer courseId, Integer roomId, Integer timeId) throws NoSuchObjectException {
        logger.debug("updating lesson with ID: {}, new professorID: {}, courseID: {}, roomID: {} and timeID: {}",
                lessonId, professorId, courseId, roomId, timeId);
        Lesson lesson = new Lesson();
        lesson.setLessonId(lessonId);
        lesson.setProfessor(professorService.readById(professorId));
        lesson.setCourse(courseService.readByID(courseId));
        lesson.setClassRoom(classRoomService.readByID(roomId));
        lesson.setTime(lessonTimeService.readByID(timeId));
        return lessonDAO.update(lesson);
    }

    public void delete(Integer lessonId) {
        logger.debug("deleting lesson with ID: {}", lessonId);
        lessonDAO.delete(lessonId);
    }

    public void addGroupToLesson(Integer groupId, Integer lessonId) {
        logger.debug("adding group with ID: {} to lesson with ID: {}", groupId, lessonId);
        lessonDAO.addGroupToLesson(groupId, lessonId);
    }

    public void deleteGroupFromLesson(Integer groupId, Integer lessonId) {
        logger.debug("deleting group with ID: {} from lesson with ID: {}", groupId, lessonId);
        lessonDAO.deleteGroupFromLesson(groupId, lessonId);
    }
}
