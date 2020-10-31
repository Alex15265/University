package com.foxminded.university.service;

import com.foxminded.university.dao.LessonTimeDAO;
import com.foxminded.university.dao.entities.LessonTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;

import java.rmi.NoSuchObjectException;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class LessonTimeService {
    private final LessonTimeDAO lessonTimeDAO;

    @Autowired
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

    public LessonTime readByID(Integer id) throws NoSuchObjectException {
        try {
        return lessonTimeDAO.readByID(id);
        } catch (EmptyResultDataAccessException e) {
            throw new NoSuchObjectException("Object not found");
        }
    }

    public LessonTime update(Integer timeId, LocalDateTime lessonStart, LocalDateTime lessonEnd) throws NoSuchObjectException {
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
