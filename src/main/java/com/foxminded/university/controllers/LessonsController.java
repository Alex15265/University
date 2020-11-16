package com.foxminded.university.controllers;

import com.foxminded.university.dao.entities.Group;
import com.foxminded.university.dao.entities.Lesson;
import com.foxminded.university.service.LessonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.rmi.NoSuchObjectException;

@Controller
public class LessonsController {
    private final Logger logger = LoggerFactory.getLogger(LessonsController.class);
    private final LessonService lessonService;

    @Autowired
    public LessonsController(LessonService lessonService) {
        this.lessonService = lessonService;
    }

    @GetMapping("/lessons")
    public String showLessons(Model model) {
        logger.debug("showing all lessons");
        model.addAttribute("lessons", lessonService.readAll());
        return "lessons/lessons";
    }

    @GetMapping("/newLessonForm")
    public String showNewLessonForm(Model model) {
        logger.debug("showing new Lesson form");
        Lesson lesson = new Lesson();
        model.addAttribute("lesson", lesson);
        return "lessons/new_lesson";
    }

    @PostMapping("/saveLesson")
    public String saveLesson(@ModelAttribute("lesson") Lesson lesson, Model model) throws NoSuchObjectException {
        logger.debug("saving new lesson: {}", lesson);
        lessonService.create(lesson.getProfessor().getProfessorId(), lesson.getCourse().getCourseId(),
                lesson.getClassRoom().getRoomId(), lesson.getTime().getTimeId());
        return "redirect:/lessons";
    }

    @GetMapping("/updateLessonForm/{id}")
    public String showUpdateLessonForm(@PathVariable("id") Integer lessonId, Model model) throws NoSuchObjectException {
        logger.debug("showing update lesson form");
        model.addAttribute("lesson", lessonService.readById(lessonId));
        return "lessons/update_lesson";
    }

    @PostMapping("/updateLesson/{id}")
    public String update(@ModelAttribute("lesson") Lesson lesson, @PathVariable("id") Integer lessonId) throws NoSuchObjectException {
        logger.debug("updating lesson with ID: {}", lessonId);
        lessonService.update(lessonId, lesson.getProfessor().getProfessorId(), lesson.getCourse().getCourseId(),
                lesson.getClassRoom().getRoomId(), lesson.getTime().getTimeId());
        return "redirect:/lessons";
    }

    @GetMapping("/deleteLesson/{id}")
    public String deleteLesson(@PathVariable("id") Integer lessonId) {
        logger.debug("deleting lesson with ID: {}", lessonId);
        lessonService.delete(lessonId);
        return "redirect:/lessons";
    }

    @GetMapping("/editGroupForm/{id}")
    public String showEditGroupForm(@PathVariable("id") Integer lessonId, Model model) throws NoSuchObjectException {
        logger.debug("showing update lesson form");
        model.addAttribute("lesson", lessonService.readById(lessonId));
        Group group = new Group();
        model.addAttribute("group", group);
        return "lessons/edit_group";
    }

    @PostMapping("/addGroup/{id}")
    public String addGroup(@ModelAttribute("groupId") Integer groupId, @PathVariable("id") Integer lessonId,
                           Model model) {
        logger.debug("adding group with ID: {} to lesson with ID: {}", groupId, lessonId);
        lessonService.addGroupToLesson(groupId, lessonId);
        return "redirect:/lessons";
    }

    @PostMapping("/deleteGroup/{id}")
    public String deleteGroup(@ModelAttribute("groupId") Integer groupId, @PathVariable("id") Integer lessonId,
                           Model model) {
        logger.debug("deleting group with ID: {} from lesson with ID: {}", groupId, lessonId);
        lessonService.deleteGroupFromLesson(groupId, lessonId);
        return "redirect:/lessons";
    }
}
