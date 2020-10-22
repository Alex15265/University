package com.foxminded.university.service;

import com.foxminded.university.dao.LessonTimeDAO;
import com.foxminded.university.dao.entities.LessonTime;

import java.time.LocalDateTime;
import java.util.List;

public class LessonTimeService {
    private final LessonTimeDAO lessonTimeDAO;

    public LessonTimeService(LessonTimeDAO lessonTimeDAO) {
        this.lessonTimeDAO = lessonTimeDAO;
    }

    public LessonTime create(LocalDateTime lessonStart, LocalDateTime lessonEnd) {
        LessonTime lessonTime = new LessonTime();
        lessonTime.setLessonStart(lessonStart);
        lessonTime.setLessonEnd(lessonEnd);
        return lessonTimeDAO.create(lessonTime);
    }

    public List<LessonTime> readAll() {
        return lessonTimeDAO.readAll();
    }

    public LessonTime readByID(Integer id) {
        return lessonTimeDAO.readByID(id);
    }

    public LessonTime update(Integer timeId, LocalDateTime lessonStart, LocalDateTime lessonEnd) {
        LessonTime lessonTime = new LessonTime();
        lessonTime.setTimeId(timeId);
        lessonTime.setLessonStart(lessonStart);
        lessonTime.setLessonEnd(lessonEnd);
        return lessonTimeDAO.update(lessonTime);
    }

    public void delete(Integer timeId) {
        lessonTimeDAO.delete(timeId);
    }
}
