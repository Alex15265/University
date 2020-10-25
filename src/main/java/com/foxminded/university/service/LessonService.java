package com.foxminded.university.service;

import com.foxminded.university.dao.LessonDAO;
import com.foxminded.university.dao.entities.Lesson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.util.List;

@Component
public class LessonService {
    private final LessonDAO lessonDAO;
    private final ProfessorService professorService;
    private final CourseService courseService;
    private final ClassRoomService classRoomService;
    private final LessonTimeService lessonTimeService;

    @Autowired
    public LessonService(LessonDAO lessonDAO, ProfessorService professorService, CourseService courseService,
                         ClassRoomService classRoomService, LessonTimeService lessonTimeService) {
        this.lessonDAO = lessonDAO;
        this.professorService = professorService;
        this.classRoomService = classRoomService;
        this.lessonTimeService = lessonTimeService;
        this.courseService = courseService;
    }

    public Lesson create(Integer professorId, Integer courseId, Integer roomId, Integer timeId) throws FileNotFoundException {
        Lesson lesson = new Lesson();
        lesson.setProfessor(professorService.readById(professorId));
        lesson.setCourse(courseService.readByID(courseId));
        lesson.setClassRoom(classRoomService.readByID(roomId));
        lesson.setTime(lessonTimeService.readByID(timeId));
        return lessonDAO.create(lesson);
    }

    public List<Lesson> readAll() {
        List<Lesson> lessons = lessonDAO.readAll();
        for (Lesson lesson: lessons) {
            lesson.setGroups(lessonDAO.readGroupsByLesson(lesson.getLessonId()));
        }
        return lessons;
    }

    public Lesson readById(Integer lessonId) throws FileNotFoundException {
        try {
        Lesson lesson = lessonDAO.readByID(lessonId);
        lesson.setGroups(lessonDAO.readGroupsByLesson(lessonId));
        return lesson;
        } catch (EmptyResultDataAccessException e) {
            throw new FileNotFoundException();
        }
    }

    public Lesson update(Integer lessonId, Integer professorId, Integer courseId, Integer roomId, Integer timeId) throws FileNotFoundException {
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
