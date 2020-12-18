package com.foxminded.university.controllers;

import com.foxminded.university.dto.lesson.LessonDTOResponse;
import com.foxminded.university.dto.timetable.TimetableDTORequest;
import com.foxminded.university.entities.Lesson;
import com.foxminded.university.mappers.LessonMapper;
import com.foxminded.university.service.TimetableService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class TimetablesRestController {
    private final Logger logger = LoggerFactory.getLogger(TimetablesRestController.class);
    private final TimetableService timetableService;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yy HH:mm");

    @GetMapping("/api/professorTimetable")
    public List<LessonDTOResponse> findByProfessor(@Valid @RequestBody TimetableDTORequest timetableDTORequest) {
        logger.debug("showing lessons by professor with ID: {}", timetableDTORequest.getProfessorId());
        List<Lesson> lessons = timetableService.findByProfessor(timetableDTORequest.getProfessorId(),
                LocalDateTime.parse(timetableDTORequest.getStartTime(), formatter),
                LocalDateTime.parse(timetableDTORequest.getEndTime(), formatter)).getListOfLessons();
        return lessons.stream().map(LessonMapper.INSTANCE::lessonToLessonDTOResponse)
                .collect(Collectors.toList());
    }

    @GetMapping("/api/groupTimetable")
    public List<LessonDTOResponse> findByGroup(@Valid @RequestBody TimetableDTORequest timetableDTORequest) {
        logger.debug("showing lessons by group with ID: {}", timetableDTORequest.getGroupId());
        List<Lesson> lessons = timetableService.findByProfessor(timetableDTORequest.getGroupId(),
                LocalDateTime.parse(timetableDTORequest.getStartTime(), formatter),
                LocalDateTime.parse(timetableDTORequest.getEndTime(), formatter)).getListOfLessons();
        return lessons.stream().map(LessonMapper.INSTANCE::lessonToLessonDTOResponse)
                .collect(Collectors.toList());
    }
}
