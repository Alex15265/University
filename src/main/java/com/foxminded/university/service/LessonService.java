package com.foxminded.university.service;

import com.foxminded.university.dao.*;
import com.foxminded.university.dao.entities.Lesson;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

public class LessonService {
    private final ClassPathXmlApplicationContext context;
    private final LessonDAO lessonDAO;
    //private final ProfessorDAO professorDAO;
    private final ProfessorService professorService;
    private final CourseService courseService;
    //private final CourseDAO courseDAO;
    //private final ClassRoomDAO classRoomDAO;
    private final ClassRoomService classRoomService;
    //private final LessonTimeDAO lessonTimeDAO;
    private final LessonTimeService lessonTimeService;

    public LessonService(String configLocation, LessonDAO lessonDAO, ProfessorService professorService, CourseService courseService,
                         ClassRoomService classRoomService, LessonTimeService lessonTimeService
                        /*ProfessorDAO professorDAO, CourseDAO courseDAO,
                         ClassRoomDAO classRoomDAO, LessonTimeDAO lessonTimeDAO*/) {
        context = new ClassPathXmlApplicationContext(configLocation);
        this.lessonDAO = lessonDAO;
        //this.professorDAO = professorDAO;
        this.professorService = professorService;
        this.classRoomService = classRoomService;
        this.lessonTimeService = lessonTimeService;
        //this.courseDAO = courseDAO;
        this.courseService = courseService;
        //this.classRoomDAO = classRoomDAO;
        //this.lessonTimeDAO = lessonTimeDAO;
    }

    public Lesson createLesson(Integer professorId, Integer courseId, Integer roomId, Integer timeId) {
        Lesson lesson = context.getBean("lesson", Lesson.class);
        lesson.setProfessor(professorService.readProfessorById(professorId));
        lesson.setCourse(courseService.readCourseByID(courseId));
        lesson.setClassRoom(classRoomService.readClassRoomByID(roomId));
        lesson.setTime(lessonTimeService.readLessonTimeByID(timeId));
        return lessonDAO.create(lesson);
    }

    public List<Lesson> readAllLessons() {
        List<Lesson> lessons = lessonDAO.readAll();
        for (Lesson lesson: lessons) {
            lesson.setGroups(lessonDAO.readGroupsByLesson(lesson.getLessonId()));
        }
        return lessons;
    }

    public Lesson readLessonById(Integer lessonId) {
        Lesson lesson = lessonDAO.readByID(lessonId);
        lesson.setGroups(lessonDAO.readGroupsByLesson(lessonId));
        return lesson;
    }

    public Lesson updateLesson(Integer lessonId, Integer professorId, Integer courseId, Integer roomId, Integer timeId) {
        Lesson lesson = context.getBean("lesson", Lesson.class);
        lesson.setLessonId(lessonId);
        lesson.setProfessor(professorService.readProfessorById(professorId));
        lesson.setCourse(courseService.readCourseByID(courseId));
        lesson.setClassRoom(classRoomService.readClassRoomByID(roomId));
        lesson.setTime(lessonTimeService.readLessonTimeByID(timeId));
        return lessonDAO.update(lesson);
    }

    public void deleteLesson(Integer lessonId) {
        lessonDAO.delete(lessonId);
    }

    public void addGroupToLesson(Integer groupId, Integer lessonId) {
        lessonDAO.addGroupToLesson(groupId, lessonId);
    }

    public void deleteGroupFromLesson(Integer groupId, Integer lessonId) {
        lessonDAO.deleteGroupFromLesson(groupId, lessonId);
    }
}
