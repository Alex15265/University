package com.foxminded.university.service;

import com.foxminded.university.entities.LessonTime;
import com.foxminded.university.repositories.LessonTimeRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LessonTimeService {
    private final LessonTimeRepository lessonTimeRepository;
    private final Logger logger = LoggerFactory.getLogger(LessonTimeService.class);

    public LessonTime create(LocalDateTime lessonStart, LocalDateTime lessonEnd) {
        logger.debug("creating lessonTime with lessonStart: {} and lessonEnd: {}", lessonStart, lessonEnd);
        LessonTime lessonTime = new LessonTime();
        lessonTime.setLessonStart(lessonStart);
        lessonTime.setLessonEnd(lessonEnd);
        return lessonTimeRepository.save(lessonTime);
    }

    public List<LessonTime> readAll() {
        logger.debug("reading all lessonTimes");
        List<LessonTime> lessonTimes = new ArrayList<>();
        lessonTimeRepository.findAll().forEach(lessonTimes::add);
        lessonTimes.sort(Comparator.comparing(LessonTime::getTimeId));
        return lessonTimes;
    }

    public LessonTime readByID(Integer lessonTimeId) {
        logger.debug("reading lessonTime with ID: {}", lessonTimeId);
        Optional<LessonTime> lessonTimeOptional = lessonTimeRepository.findById(lessonTimeId);
        LessonTime lessonTime = new LessonTime();
        if (lessonTimeOptional.isPresent()) {
            lessonTime = lessonTimeOptional.get();
        }
        return lessonTime;
    }

    public LessonTime update(Integer timeId, LocalDateTime lessonStart, LocalDateTime lessonEnd) {
        logger.debug("updating lessonTime with ID: {}, new lessonStart: {} and lessonEnd: {}",
                timeId, lessonStart, lessonEnd);
        LessonTime lessonTime = new LessonTime();
        lessonTime.setTimeId(timeId);
        lessonTime.setLessonStart(lessonStart);
        lessonTime.setLessonEnd(lessonEnd);
        return lessonTimeRepository.save(lessonTime);
    }

    public void delete(Integer timeId) {
        logger.debug("deleting lessonTime with ID: {}", timeId);
        lessonTimeRepository.deleteById(timeId);
    }
}
