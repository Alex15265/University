package com.foxminded.university.service;

import com.foxminded.university.dao.LessonTimeDAO;
import com.foxminded.university.dao.entities.LessonTime;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.time.LocalDateTime;
import java.util.List;

public class LessonTimeService {
    private final ClassPathXmlApplicationContext context;
    private final LessonTimeDAO lessonTimeDAO;

    public LessonTimeService(String configLocation, LessonTimeDAO lessonTimeDAO) {
        context = new ClassPathXmlApplicationContext(configLocation);
        this.lessonTimeDAO = lessonTimeDAO;
    }

    public void createLessonTime(LocalDateTime lessonStart, LocalDateTime lessonEnd) {
        LessonTime lessonTime = context.getBean("lessonTime", LessonTime.class);
        lessonTime.setLessonStart(lessonStart);
        lessonTime.setLessonEnd(lessonEnd);
        lessonTimeDAO.create(lessonTime);
    }

    public List<LessonTime> readAllLessonTimes() {
        return lessonTimeDAO.readAll();
    }

    public LessonTime readLessonTimeByID(Integer id) {
        return lessonTimeDAO.readByID(id);
    }

    public void updateLessonTime(Integer timeId, LocalDateTime lessonStart, LocalDateTime lessonEnd) {
        LessonTime lessonTime = context.getBean("lessonTime", LessonTime.class);
        lessonTime.setTimeId(timeId);
        lessonTime.setLessonStart(lessonStart);
        lessonTime.setLessonEnd(lessonEnd);
        lessonTimeDAO.update(lessonTime);
    }

    public void deleteLessonTime(Integer timeId) {
        lessonTimeDAO.delete(timeId);
    }
}
