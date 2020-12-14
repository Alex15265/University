package com.foxminded.university.controllers;

import com.foxminded.university.entities.Lesson;
import com.foxminded.university.service.LessonService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class LessonsRestController {
    private final Logger logger = LoggerFactory.getLogger(LessonsRestController.class);
    private final LessonService lessonService;

    @GetMapping("/api/lessons")
    public List<Lesson> showLessons() {
        logger.debug("showing all lessons");
        return lessonService.readAll();
    }

    @GetMapping("/api/lessons/{id}")
    public Lesson showLesson(@PathVariable("id") Integer lessonId) {
        logger.debug("showing lesson with ID: {}", lessonId);
        return lessonService.readById(lessonId);
    }

    @GetMapping("/api/saveLesson")
    public Lesson saveLesson(@RequestParam Integer professorId, @RequestParam Integer courseId,
                             @RequestParam Integer roomId, @RequestParam Integer timeId) {
        logger.debug("saving new lesson with professorId: {}, courseId: {}, roomId: {}, timeId: {}",
                professorId, courseId, roomId, timeId);
        return lessonService.create(professorId, courseId, roomId, timeId);
    }

    @GetMapping("/api/updateLesson/{id}")
    public Lesson update(@RequestParam Integer professorId, @RequestParam Integer courseId, @RequestParam Integer roomId,
                         @RequestParam Integer timeId, @PathVariable("id") Integer lessonId) {
        logger.debug("updating lesson with ID: {}", lessonId);
        return lessonService.update(lessonId, professorId, courseId, roomId, timeId);
    }

    @GetMapping("/api/deleteLesson/{id}")
    public void deleteLesson(@PathVariable("id") Integer lessonId) {
        logger.debug("deleting lesson with ID: {}", lessonId);
        lessonService.delete(lessonId);
    }

    @GetMapping("/api/addGroupToLesson/{id}")
    public void addGroup(@RequestParam Integer groupId, @PathVariable("id") Integer lessonId) {
        logger.debug("adding group with ID: {} to lesson with ID: {}", groupId, lessonId);
        lessonService.addGroupToLesson(groupId, lessonId);
    }

    @GetMapping("/api/deleteGroupFromLesson/{id}")
    public void deleteGroup(@RequestParam Integer groupId, @PathVariable("id") Integer lessonId) {
        logger.debug("deleting group with ID: {} from lesson with ID: {}", groupId, lessonId);
        lessonService.deleteGroupFromLesson(groupId, lessonId);
    }
}