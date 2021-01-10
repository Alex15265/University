package com.foxminded.university.controllers.rest;

import com.foxminded.university.dto.courses.CourseDTOResponse;
import com.foxminded.university.dto.professor.ProfessorDTORequest;
import com.foxminded.university.dto.professor.ProfessorDTOResponse;
import com.foxminded.university.entities.Course;
import com.foxminded.university.entities.Professor;
import com.foxminded.university.mappers.CourseMapper;
import com.foxminded.university.mappers.ProfessorMapper;
import com.foxminded.university.service.ProfessorService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/professors")
public class ProfessorsRestController {
    private final Logger logger = LoggerFactory.getLogger(ProfessorsRestController.class);
    private final ProfessorService professorService;

    @GetMapping("/")
    @PreAuthorize("hasAuthority('read')")
    @ApiOperation(value = "Method used to fetch all professors")
    public List<ProfessorDTOResponse> showAllProfessors() {
        logger.debug("showing all professors");
        List<Professor> professors = professorService.readAll();
        return professors.stream().map(ProfessorMapper.INSTANCE::professorToProfessorDTOResponse)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('read')")
    @ApiOperation(value = "Method used to fetch a professor by ID")
    public ProfessorDTOResponse showProfessorByID(
            @ApiParam(value = "ID value of the professor you need to retrieve", required = true)
            @PathVariable("id") Integer professorId) {
        logger.debug("showing professor with ID: {}", professorId);
        Professor professor = professorService.readById(professorId);
        return ProfessorMapper.INSTANCE.professorToProfessorDTOResponse(professor);
    }

    @GetMapping("/{id}/courses")
    @PreAuthorize("hasAuthority('read')")
    @ApiOperation(value = "Method used to show professor's courses")
    public List<CourseDTOResponse> showCoursesByProfessor(
            @ApiParam(value = "ID value of the professor whose courses you want to retrieve", required = true)
            @PathVariable("id") Integer professorId) {
        logger.debug("showing courses by professor with ID: {}", professorId);
        List<Course> courses = professorService.findCoursesByProfessor(professorId);
        return courses.stream().map(CourseMapper.INSTANCE::courseToCourseDTOResponse)
                .collect(Collectors.toList());
    }

    @PostMapping("/")
    @PreAuthorize("hasAuthority('write')")
    @ApiOperation(value = "Method used to save new professor")
    public ProfessorDTOResponse saveProfessor(
            @ApiParam(value = "professorDTORequest for the professor you need to save", required = true)
            @Valid @RequestBody ProfessorDTORequest professorDTORequest) {
        logger.debug("saving new professor: {}", professorDTORequest);
        Professor professor = professorService.create(professorDTORequest.getFirstName(),
                professorDTORequest.getLastName());
        return ProfessorMapper.INSTANCE.professorToProfessorDTOResponse(professor);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasAuthority('write')")
    @ApiOperation(value = "Method used to update a professor by ID")
    public ProfessorDTOResponse updateProfessor(
            @ApiParam(value = "professorDTORequest for the professor you need to update", required = true)
            @Valid @RequestBody ProfessorDTORequest professorDTORequest,
            @ApiParam(value = "ID value of the professor you need to update", required = true)
            @PathVariable("id") Integer professorId) {
        logger.debug("updating professor with ID: {}", professorId);
        Professor professor = professorService.update(professorId, professorDTORequest.getFirstName(),
                professorDTORequest.getLastName());
        return ProfessorMapper.INSTANCE.professorToProfessorDTOResponse(professor);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('write')")
    @ApiOperation(value = "Method used to delete a professor by ID")
    public void deleteProfessor(
            @ApiParam(value = "ID value of the professor you need to delete", required = true)
            @PathVariable("id") Integer professorId) {
        logger.debug("deleting professor with ID: {}", professorId);
        professorService.delete(professorId);
    }
}
