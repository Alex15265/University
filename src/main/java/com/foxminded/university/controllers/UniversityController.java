package com.foxminded.university.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UniversityController {
    private final Logger logger = LoggerFactory.getLogger(StudentsController.class);

    @GetMapping("/")
    @PreAuthorize("hasAuthority('read')")
    public String showUniversity(Model model) {
        logger.debug("showing university");
        return "views/university/index";
    }
}
