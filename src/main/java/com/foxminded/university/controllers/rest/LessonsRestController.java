package com.foxminded.university.controllers.rest;

import com.foxminded.university.dto.lesson.LessonDTORequest;
import com.foxminded.university.dto.lesson.LessonDTOResponse;
import com.foxminded.university.entities.Lesson;
import com.foxminded.university.mappers.LessonMapper;
import com.foxminded.university.service.LessonService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/lessons")
public class LessonsRestController {
    private final Logger logger = LoggerFactory.getLogger(LessonsRestController.class);
    private final LessonService lessonService;

    @GetMapping("/")
    @ApiOperation(value = "Method used to fetch all lessons")
    public List<LessonDTOResponse> showAllLessons() {
        logger.debug("showing all lessons");
        List<Lesson> lessons = lessonService.readAll();
        return lessons.stream().map(LessonMapper.INSTANCE::lessonToLessonDTOResponse).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Method used to fetch a lesson by ID")
    public LessonDTOResponse showLessonByID(
            @ApiParam(value = "ID value of the lesson you need to retrieve", required = true)
            @PathVariable("id") Integer lessonId) {
        logger.debug("showing lesson with ID: {}", lessonId);
        Lesson lesson = lessonService.readById(lessonId);
        return LessonMapper.INSTANCE.lessonToLessonDTOResponse(lesson);
    }

    @PostMapping("/")
    @ApiOperation(value = "Method used to save a new lesson")
    public LessonDTOResponse saveLesson(
            @ApiParam(value = "lessonDTORequest for the lesson you need to save", required = true)
            @Valid @RequestBody LessonDTORequest lessonDTORequest) {
        logger.debug("saving new lesson: {}", lessonDTORequest);
        Lesson lesson = lessonService.create(lessonDTORequest.getProfessorId(), lessonDTORequest.getCourseId(),
                lessonDTORequest.getRoomId(), lessonDTORequest.getTimeId());
        return LessonMapper.INSTANCE.lessonToLessonDTOResponse(lesson);
    }

    @PatchMapping("/{id}")
    @ApiOperation(value = "Method used to update a lesson by ID")
    public LessonDTOResponse updateLesson(
            @ApiParam(value = "lessonDTORequest for the lesson you need to update", required = true)
            @Valid @RequestBody LessonDTORequest lessonDTORequest,
            @ApiParam(value = "ID value of the lesson you need to update", required = true)
            @PathVariable("id") Integer lessonId) {
        logger.debug("updating lesson with ID: {}", lessonId);
        Lesson lesson = lessonService.update(lessonId, lessonDTORequest.getProfessorId(), lessonDTORequest.getCourseId(),
                lessonDTORequest.getRoomId(), lessonDTORequest.getTimeId());
        return LessonMapper.INSTANCE.lessonToLessonDTOResponse(lesson);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Method used to delete a lesson by ID")
    public void deleteLesson(
            @ApiParam(value = "ID value of the lesson you need to delete", required = true)
            @PathVariable("id") Integer lessonId) {
        logger.debug("deleting lesson with ID: {}", lessonId);
        lessonService.delete(lessonId);
    }

    @PostMapping("/{id}/groups")
    @ApiOperation(value = "Method used to add a group to a lesson")
    public void addGroupToLesson(
            @ApiParam(value = "ID value of the group you need to add", required = true)
            @RequestParam Integer groupId,
            @ApiParam(value = "ID value of the lesson whose groups you need to update", required = true)
            @PathVariable("id") Integer lessonId) {
        logger.debug("adding group with ID: {} to lesson with ID: {}", groupId, lessonId);
        lessonService.addGroupToLesson(groupId, lessonId);
    }

    @DeleteMapping("/{id}/groups")
    @ApiOperation(value = "Method used to delete a group from a lesson")
    public void deleteGroupFromLesson(
            @ApiParam(value = "ID value of the group you need to delete", required = true)
            @RequestParam Integer groupId,
            @ApiParam(value = "ID value of the lesson whose groups you need to update", required = true)
            @PathVariable("id") Integer lessonId) {
        logger.debug("deleting group with ID: {} from lesson with ID: {}", groupId, lessonId);
        lessonService.deleteGroupFromLesson(groupId, lessonId);
    }
}