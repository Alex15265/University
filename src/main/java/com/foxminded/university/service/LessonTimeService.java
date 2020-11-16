package com.foxminded.university.service;

import com.foxminded.university.dao.LessonTimeDAO;
import com.foxminded.university.dao.entities.LessonTime;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;

import java.rmi.NoSuchObjectException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
@RequiredArgsConstructor
public class LessonTimeService {
    private final LessonTimeDAO lessonTimeDAO;
    private final Logger logger = LoggerFactory.getLogger(LessonTimeService.class);

    public LessonTime create(LocalDateTime lessonStart, LocalDateTime lessonEnd) {
        logger.debug("creating lessonTime with lessonStart: {} and lessonEnd: {}", lessonStart, lessonEnd);
        LessonTime lessonTime = new LessonTime();
        lessonTime.setLessonStart(lessonStart);
        lessonTime.setLessonEnd(lessonEnd);
        return lessonTimeDAO.create(lessonTime);
    }

    public List<LessonTime> readAll() {
        logger.debug("reading all lessonTimes");
        return lessonTimeDAO.readAll();
    }

    public LessonTime readByID(Integer lessonTimeId) throws NoSuchObjectException {
        logger.debug("reading lessonTime with ID: {}", lessonTimeId);
        try {
        return lessonTimeDAO.readByID(lessonTimeId);
        } catch (EmptyResultDataAccessException e) {
            logger.warn("reading lessonTime with ID: {} exception: {}", lessonTimeId, e.getMessage());
            throw new NoSuchObjectException("Object not found");
        }
    }

    public LessonTime update(Integer timeId, LocalDateTime lessonStart, LocalDateTime lessonEnd) throws NoSuchObjectException {
        logger.debug("updating lessonTime with ID: {}, new lessonStart: {} and lessonEnd: {}",
                timeId, lessonStart, lessonEnd);
        LessonTime lessonTime = new LessonTime();
        lessonTime.setTimeId(timeId);
        lessonTime.setLessonStart(lessonStart);
        lessonTime.setLessonEnd(lessonEnd);
        return lessonTimeDAO.update(lessonTime);
    }

    public void delete(Integer timeId) {
        logger.debug("deleting lessonTime with ID: {}", timeId);
        lessonTimeDAO.delete(timeId);
    }
}
