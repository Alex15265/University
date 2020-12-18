package com.foxminded.university.controllers;

import com.foxminded.university.dto.courses.CourseDTOResponse;
import com.foxminded.university.dto.professor.ProfessorDTORequest;
import com.foxminded.university.dto.professor.ProfessorDTOResponse;
import com.foxminded.university.entities.Course;
import com.foxminded.university.entities.Professor;
import com.foxminded.university.mappers.CourseMapper;
import com.foxminded.university.mappers.ProfessorMapper;
import com.foxminded.university.service.ProfessorService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class ProfessorsRestController {
    private final Logger logger = LoggerFactory.getLogger(ProfessorsRestController.class);
    private final ProfessorService professorService;

    @GetMapping("/api/professors")
    public List<ProfessorDTOResponse> showProfessors() {
        logger.debug("showing all professors");
        List<Professor> professors = professorService.readAll();
        return professors.stream().map(ProfessorMapper.INSTANCE::professorToProfessorDTOResponse)
                .collect(Collectors.toList());
    }

    @GetMapping("/api/professors/{id}")
    public ProfessorDTOResponse showProfessor(@PathVariable("id") Integer professorId) {
        logger.debug("showing professor with ID: {}", professorId);
        Professor professor = professorService.readById(professorId);
        return ProfessorMapper.INSTANCE.professorToProfessorDTOResponse(professor);
    }

    @GetMapping("/api/professors/{id}/courses")
    public List<CourseDTOResponse> showCoursesByProfessor(@PathVariable("id") Integer professorId) {
        logger.debug("showing courses by professor with ID: {}", professorId);
        List<Course> courses = professorService.findCoursesByProfessor(professorId);
        return courses.stream().map(CourseMapper.INSTANCE::courseToCourseDTOResponse)
                .collect(Collectors.toList());
    }

    @PostMapping("/api/professors")
    public ProfessorDTOResponse saveProfessor(@Valid @RequestBody ProfessorDTORequest professorDTORequest) {
        logger.debug("saving new professor: {}", professorDTORequest);
        Professor professor = professorService.create(professorDTORequest.getFirstName(),
                professorDTORequest.getLastName());
        return ProfessorMapper.INSTANCE.professorToProfessorDTOResponse(professor);
    }

    @PatchMapping("/api/professors/{id}")
    public ProfessorDTOResponse update(@Valid @RequestBody ProfessorDTORequest professorDTORequest,
                          @PathVariable("id") Integer professorId) {
        logger.debug("updating professor with ID: {}", professorId);
        Professor professor = professorService.update(professorId, professorDTORequest.getFirstName(),
                professorDTORequest.getLastName());
        return ProfessorMapper.INSTANCE.professorToProfessorDTOResponse(professor);
    }

    @DeleteMapping("/api/professors/{id}")
    public void deleteProfessor(@PathVariable("id") Integer professorId) {
        logger.debug("deleting professor with ID: {}", professorId);
        professorService.delete(professorId);
    }
}
