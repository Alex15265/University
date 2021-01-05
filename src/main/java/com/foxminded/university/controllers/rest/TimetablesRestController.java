package com.foxminded.university.controllers.rest;

import com.foxminded.university.dto.lesson.LessonDTOResponse;
import com.foxminded.university.entities.Lesson;
import com.foxminded.university.mappers.LessonMapper;
import com.foxminded.university.service.TimetableService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
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
    @PreAuthorize("hasAuthority('read')")
    @ApiOperation(value = "Method used to fetch a professor's timetable")
    public List<LessonDTOResponse> findTimetableForProfessor(
                @Valid
                @Positive(message = "This field must be Positive Number")
                @ApiParam(value = "The unique id of the professor which timetable you need to retrieve")
                Integer professorId,
                @Valid
                @Pattern(message = "Invalid data format", regexp = "[0-9]{2}.[0-9]{2}.[0-9]{2} [0-9]{1,2}:[0-9]{2}")
                @ApiParam(value = "The start time of lessons you need to retrieve")
                String startTime,
                @Valid
                @Pattern(message = "Invalid data format", regexp = "[0-9]{2}.[0-9]{2}.[0-9]{2} [0-9]{1,2}:[0-9]{2}")
                @ApiParam(value = "The end time of lessons you need to retrieve")
                String endTime) {
        logger.debug("showing lessons by professor with ID: {}", professorId);
        List<Lesson> lessons = timetableService.findByProfessor(
                professorId,
                LocalDateTime.parse(startTime, formatter),
                LocalDateTime.parse(endTime, formatter))
                .getListOfLessons();
        return lessons.stream().map(LessonMapper.INSTANCE::lessonToLessonDTOResponse)
                .collect(Collectors.toList());
    }

    @GetMapping("/api/groupTimetable")
    @PreAuthorize("hasAuthority('read')")
    @ApiOperation(value = "Method used to fetch a group's timetable")
    public List<LessonDTOResponse> findTimetableForGroup(
            @Valid
            @Positive(message = "This field must be Positive Number")
            @ApiParam(value = "The unique id of the group which timetable you need to retrieve")
                    Integer groupId,
            @Valid
            @Pattern(message = "Invalid data format", regexp = "[0-9]{2}.[0-9]{2}.[0-9]{2} [0-9]{1,2}:[0-9]{2}")
            @ApiParam(value = "The start time of lessons you need to retrieve")
                    String startTime,
            @Valid
            @Pattern(message = "Invalid data format", regexp = "[0-9]{2}.[0-9]{2}.[0-9]{2} [0-9]{1,2}:[0-9]{2}")
            @ApiParam(value = "The end time of lessons you need to retrieve")
                    String endTime) {
        logger.debug("showing lessons by group with ID: {}", groupId);
        List<Lesson> lessons = timetableService.findByGroup(
                groupId,
                LocalDateTime.parse(startTime, formatter),
                LocalDateTime.parse(endTime, formatter))
                .getListOfLessons();
        return lessons.stream().map(LessonMapper.INSTANCE::lessonToLessonDTOResponse)
                .collect(Collectors.toList());
    }
}
