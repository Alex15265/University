package com.foxminded.university.controllers;

import com.foxminded.university.dto.lesson.LessonDTORequest;
import com.foxminded.university.dto.lesson.LessonDTOResponse;
import com.foxminded.university.entities.Lesson;
import com.foxminded.university.mappers.LessonMapper;
import com.foxminded.university.service.LessonService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class LessonsRestController {
    private final Logger logger = LoggerFactory.getLogger(LessonsRestController.class);
    private final LessonService lessonService;

    @GetMapping("/api/lessons")
    public List<LessonDTOResponse> showLessons() {
        logger.debug("showing all lessons");
        List<Lesson> lessons = lessonService.readAll();
        return lessons.stream().map(LessonMapper.INSTANCE::lessonToLessonDTOResponse).collect(Collectors.toList());
    }

    @GetMapping("/api/lessons/{id}")
    public LessonDTOResponse showLesson(@PathVariable("id") Integer lessonId) {
        logger.debug("showing lesson with ID: {}", lessonId);
        Lesson lesson = lessonService.readById(lessonId);
        return LessonMapper.INSTANCE.lessonToLessonDTOResponse(lesson);
    }

    @PostMapping("/api/lessons")
    public LessonDTOResponse saveLesson(@Valid @RequestBody LessonDTORequest lessonDTORequest) {
        logger.debug("saving new lesson: {}", lessonDTORequest);
        Lesson lesson = lessonService.create(lessonDTORequest.getProfessorId(), lessonDTORequest.getCourseId(),
                lessonDTORequest.getRoomId(), lessonDTORequest.getTimeId());
        return LessonMapper.INSTANCE.lessonToLessonDTOResponse(lesson);
    }

    @PatchMapping("/api/lessons/{id}")
    public LessonDTOResponse update(@Valid @RequestBody LessonDTORequest lessonDTORequest, @PathVariable("id") Integer lessonId) {
        logger.debug("updating lesson with ID: {}", lessonId);
        Lesson lesson = lessonService.update(lessonId, lessonDTORequest.getProfessorId(), lessonDTORequest.getCourseId(),
                lessonDTORequest.getRoomId(), lessonDTORequest.getTimeId());
        return LessonMapper.INSTANCE.lessonToLessonDTOResponse(lesson);
    }

    @DeleteMapping("/api/lessons/{id}")
    public void deleteLesson(@PathVariable("id") Integer lessonId) {
        logger.debug("deleting lesson with ID: {}", lessonId);
        lessonService.delete(lessonId);
    }

    @PostMapping("/api/lessons/{id}/groups")
    public void addGroup(@RequestParam Integer groupId, @PathVariable("id") Integer lessonId) {
        logger.debug("adding group with ID: {} to lesson with ID: {}", groupId, lessonId);
        lessonService.addGroupToLesson(groupId, lessonId);
    }

    @DeleteMapping("/api/lessons/{id}/groups")
    public void deleteGroup(@RequestParam Integer groupId, @PathVariable("id") Integer lessonId) {
        logger.debug("deleting group with ID: {} from lesson with ID: {}", groupId, lessonId);
        lessonService.deleteGroupFromLesson(groupId, lessonId);
    }
}