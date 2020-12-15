package com.foxminded.university.controllers;

import com.foxminded.university.entities.Course;
import com.foxminded.university.entities.Professor;
import com.foxminded.university.service.ProfessorService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProfessorsRestController {
    private final Logger logger = LoggerFactory.getLogger(ProfessorsRestController.class);
    private final ProfessorService professorService;

    @GetMapping("/api/professors")
    public List<Professor> showProfessors() {
        logger.debug("showing all professors");
        return professorService.readAll();
    }

    @GetMapping("/api/professors/{id}")
    public Professor showProfessor(@PathVariable("id") Integer professorId) {
        logger.debug("showing professor with ID: {}", professorId);
        return professorService.readById(professorId);
    }

    @GetMapping("/api/professors/{id}/courses")
    public List<Course> showCoursesByProfessor(@PathVariable("id") Integer professorId) {
        logger.debug("showing courses by professor with ID: {}", professorId);
        return professorService.findCoursesByProfessor(professorId);
    }

    @PostMapping("/api/professors")
    public Professor saveProfessor(@RequestParam String firstName, @RequestParam String lastName) {
        logger.debug("saving new professor with firstName: {}, lastName: {}", firstName, lastName);
        return professorService.create(firstName, lastName);
    }

    @PatchMapping("/api/professors/{id}")
    public Professor update(@RequestParam String firstName, @RequestParam String lastName,
                          @PathVariable("id") Integer professorId) {
        logger.debug("updating professor with ID: {}", professorId);
        return professorService.update(professorId, firstName, lastName);
    }

    @DeleteMapping("/api/professors/{id}")
    public void deleteProfessor(@PathVariable("id") Integer professorId) {
        logger.debug("deleting professor with ID: {}", professorId);
        professorService.delete(professorId);
    }
}
