package com.foxminded.university.controllers;

import com.foxminded.university.dao.entities.Group;
import com.foxminded.university.dao.entities.LessonTime;
import com.foxminded.university.dao.entities.Professor;
import com.foxminded.university.service.TimetableService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
@RequiredArgsConstructor
public class TimetablesController {
    private final Logger logger = LoggerFactory.getLogger(TimetablesController.class);
    private final TimetableService timetableService;

    @GetMapping("/timetableForm")
    public String showTimetableForm(Model model) {
        logger.debug("showing timetable form");
        Group group = new Group();
        Professor professor = new Professor();
        LessonTime time = new LessonTime();
        model.addAttribute("group", group);
        model.addAttribute("professor", professor);
        model.addAttribute("time", time);
        return "timetables/timetable_form";
    }

    @GetMapping("/findByProfessor")
    public String findByProfessor(@ModelAttribute("professor") Professor professor,
                                  @ModelAttribute("time") LessonTime lessonTime, Model model) {
        logger.debug("showing lessons by professor with ID: {}", professor.getProfessorId());
        model.addAttribute("lessons", timetableService.findByProfessor(professor.getProfessorId(),
                lessonTime.getLessonStart(), lessonTime.getLessonEnd()).getListOfLessons());
        return "timetables/timetable";
    }

    @GetMapping("/findByGroup")
    public String findByGroup(@ModelAttribute("group") Group group,
                                  @ModelAttribute("time") LessonTime lessonTime, Model model) {
        logger.debug("showing lessons by group with ID: {}", group.getGroupId());
        model.addAttribute("lessons", timetableService.findByGroup(group.getGroupId(),
                lessonTime.getLessonStart(), lessonTime.getLessonEnd()).getListOfLessons());
        return "timetables/timetable";
    }
}
