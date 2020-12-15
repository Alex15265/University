package com.foxminded.university.controllers;

import com.foxminded.university.entities.Lesson;
import com.foxminded.university.service.TimetableService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class TimetablesRestController {
    private final Logger logger = LoggerFactory.getLogger(TimetablesRestController.class);
    private final TimetableService timetableService;

    @GetMapping("/api/professorTimetable")
    public List<Lesson> findByProfessor(@RequestParam Integer professorId, @RequestParam String startTime,
                                        @RequestParam String endTime) {
        logger.debug("showing lessons by professor with ID: {}", professorId);
        return timetableService.findByProfessor(professorId,
                LocalDateTime.parse(startTime), LocalDateTime.parse(endTime)).getListOfLessons();
    }

    @GetMapping("/api/groupTimetable")
    public List<Lesson> findByGroup(@RequestParam Integer groupId, @RequestParam String startTime,
                                        @RequestParam String endTime) {
        logger.debug("showing lessons by group with ID: {}", groupId);
        return timetableService.findByGroup(groupId,
                LocalDateTime.parse(startTime), LocalDateTime.parse(endTime)).getListOfLessons();
    }
}
