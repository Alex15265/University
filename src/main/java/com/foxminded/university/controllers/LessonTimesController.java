package com.foxminded.university.controllers;

import com.foxminded.university.entities.LessonTime;
import com.foxminded.university.service.LessonTimeService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class LessonTimesController {
    private final Logger logger = LoggerFactory.getLogger(LessonTimesController.class);
    private final LessonTimeService lessonTimeService;

    @GetMapping("/lessonTimes")
    public String showLessonTimes(Model model) {
        logger.debug("showing all lessonTimes");
        model.addAttribute("lessonTimes", lessonTimeService.readAll());
        return "views/lessontimes/lessontimes";
    }

    @GetMapping("/newLessonTimeForm")
    public String showNewLessonTimeForm(Model model) {
        logger.debug("showing new LessonTime form");
        LessonTime lessonTime = new LessonTime();
        model.addAttribute("lessonTime", lessonTime);
        return "views/lessontimes/new_lessontime";
    }

    @PostMapping("/saveLessonTime")
    public String saveLessonTime(@ModelAttribute("lessonTime") LessonTime lessonTime) {
        logger.debug("saving new lessonTime: {}", lessonTime);
        lessonTimeService.create(lessonTime.getLessonStart(), lessonTime.getLessonEnd());
        return "redirect:/lessonTimes";
    }

    @GetMapping("/updateLessonTimeForm/{id}")
    public String showUpdateLessonTimeForm(@PathVariable("id") Integer timeId, Model model) {
        logger.debug("showing update lessonTime form");
        model.addAttribute("lessonTime", lessonTimeService.readByID(timeId));
        return "views/lessontimes/update_lessontime";
    }

    @PostMapping("/updateLessonTime/{id}")
    public String update(@ModelAttribute("lessonTime") LessonTime lessonTime, @PathVariable("id") Integer timeId) {
        logger.debug("updating lessonTime with ID: {}", timeId);
        lessonTimeService.update(timeId, lessonTime.getLessonStart(), lessonTime.getLessonEnd());
        return "redirect:/lessonTimes";
    }

    @GetMapping("/deleteLessonTime/{id}")
    public String deleteLessonTime(@PathVariable("id") Integer timeId) {
        logger.debug("deleting lessonTime with ID: {}", timeId);
        lessonTimeService.delete(timeId);
        return "redirect:/lessonTimes";
    }
}
