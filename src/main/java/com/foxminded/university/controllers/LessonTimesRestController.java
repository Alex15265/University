package com.foxminded.university.controllers;

import com.foxminded.university.entities.LessonTime;
import com.foxminded.university.service.LessonTimeService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class LessonTimesRestController {
    private final Logger logger = LoggerFactory.getLogger(LessonTimesRestController.class);
    private final LessonTimeService lessonTimeService;

    @GetMapping("/api/lessonTimes")
    public List<LessonTime> showLessonTimes() {
        logger.debug("showing all lessonTimes");
        return lessonTimeService.readAll();
    }

    @GetMapping("/api/lessonTimes/{id}")
    public LessonTime showLessonTime(@PathVariable("id") Integer timeId) {
        logger.debug("showing lessonTime with ID: {}", timeId);
        return lessonTimeService.readByID(timeId);
    }

    @GetMapping("/api/saveLessonTime")
    public LessonTime saveLessonTime(@RequestParam String lessonStart, @RequestParam String lessonEnd) {
        logger.debug("saving new lessonTime with lessonStart: {}, lessonEnd: {}", lessonStart, lessonEnd);
        return lessonTimeService.create(LocalDateTime.parse(lessonStart), LocalDateTime.parse(lessonEnd));
    }

    @GetMapping("/api/updateLessonTime/{id}")
    public LessonTime update(@RequestParam String lessonStart, @RequestParam String lessonEnd,
                             @PathVariable("id") Integer timeId) {
        logger.debug("updating lessonTime with ID: {}", timeId);
        return lessonTimeService.update(timeId, LocalDateTime.parse(lessonStart), LocalDateTime.parse(lessonEnd));
    }

    @GetMapping("/api/deleteLessonTime/{id}")
    public void deleteLessonTime(@PathVariable("id") Integer timeId) {
        logger.debug("deleting lessonTime with ID: {}", timeId);
        lessonTimeService.delete(timeId);
    }
}

