package com.foxminded.university.controllers;

import com.foxminded.university.entities.Professor;
import com.foxminded.university.service.ProfessorService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class ProfessorsController {
    private final Logger logger = LoggerFactory.getLogger(ProfessorsController.class);
    private final ProfessorService professorService;

    @GetMapping("/professors")
    @PreAuthorize("hasAuthority('read')")
    public String showProfessors(Model model) {
        logger.debug("showing all professors");
        model.addAttribute("professors", professorService.readAll());
        return "views/professors/professors";
    }

    @GetMapping("/coursesByProfessor/{id}")
    @PreAuthorize("hasAuthority('read')")
    public String showCoursesByProfessor(@PathVariable("id") Integer professorId, Model model) {
        logger.debug("showing courses by professor with ID: {}", professorId);
        model.addAttribute("courses", professorService.findCoursesByProfessor(professorId));
        return "views/professors/courses_by_professor";
    }

    @GetMapping("/newProfessorForm")
    @PreAuthorize("hasAuthority('write')")
    public String showNewProfessorForm(Model model) {
        logger.debug("showing new Professor form");
        Professor professor = new Professor();
        model.addAttribute("professor", professor);
        return "views/professors/new_professor";
    }

    @PostMapping("/saveProfessor")
    @PreAuthorize("hasAuthority('write')")
    public String saveProfessor(@ModelAttribute("professor") @Valid Professor professor, Errors errors) {
        logger.debug("saving new professor: {}", professor);
        if (errors.hasErrors()) {
            return "views/professors/new_professor";
        }
        professorService.create(professor.getFirstName(), professor.getLastName());
        return "redirect:/professors";
    }

    @GetMapping("/updateProfessorForm/{id}")
    @PreAuthorize("hasAuthority('write')")
    public String showUpdateProfessorForm(@PathVariable("id") Integer professorId, Model model) {
        logger.debug("showing update professor form");
        model.addAttribute("professor", professorService.readById(professorId));
        return "views/professors/update_professor";
    }

    @PostMapping("/updateProfessor/{id}")
    @PreAuthorize("hasAuthority('write')")
    public String update(@ModelAttribute("professor") Professor professor, @PathVariable("id") Integer professorId) {
        logger.debug("updating professor with ID: {}", professorId);
        professorService.update(professorId, professor.getFirstName(), professor.getLastName());
        return "redirect:/professors";
    }

    @GetMapping("/deleteProfessor/{id}")
    @PreAuthorize("hasAuthority('write')")
    public String deleteProfessor(@PathVariable("id") Integer professorId) {
        logger.debug("deleting professor with ID: {}", professorId);
        professorService.delete(professorId);
        return "redirect:/professors";
    }
}