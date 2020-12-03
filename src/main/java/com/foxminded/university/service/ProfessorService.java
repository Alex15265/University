package com.foxminded.university.service;

import com.foxminded.university.entities.Course;
import com.foxminded.university.entities.Professor;
import com.foxminded.university.repositories.ProfessorRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProfessorService {
    private final ProfessorRepository professorRepository;
    private final Logger logger = LoggerFactory.getLogger(ProfessorService.class);

    public Professor create(String firstName, String lastName) {
        logger.debug("creating professor with firstName: {} and lastName: {}", firstName, lastName);
        Professor professor = new Professor();
        professor.setFirstName(firstName);
        professor.setLastName(lastName);
        return professorRepository.save(professor);
    }

    public List<Professor> readAll() {
        logger.debug("reading all professors");
        return professorRepository.findByOrderByProfessorIdAsc();
    }

    public Professor readById(Integer professorId) {
        logger.debug("reading professor with ID: {}", professorId);
        Optional<Professor> professorOptional = professorRepository.findById(professorId);
        if (!professorOptional.isPresent()) {
            throw new EmptyResultDataAccessException("Professor not found", 1);
        }
        return professorOptional.get();
    }

    public Professor update(Integer professorId, String firstName, String lastName) {
        logger.debug("updating professor with ID: {}, new firstName: {} and lastName: {}",
                professorId, firstName, lastName);
        if (!professorRepository.existsById(professorId)) {
            throw new EmptyResultDataAccessException("Professor not found", 1);
        }
        Professor professor = new Professor();
        professor.setProfessorId(professorId);
        professor.setFirstName(firstName);
        professor.setLastName(lastName);
        return professorRepository.save(professor);
    }

    public void delete(Integer professorId) {
        logger.debug("deleting professor with ID: {}", professorId);
        if (!professorRepository.existsById(professorId)) {
            throw new EmptyResultDataAccessException("Professor not found", 1);
        }
        professorRepository.deleteById(professorId);
    }

    public List<Course> findCoursesByProfessor(Integer professorId) {
        logger.debug("finding courses by professor with ID: {}", professorId);
        Optional<Professor> professorOptional = professorRepository.findById(professorId);
        if (!professorOptional.isPresent()) {
            throw new EmptyResultDataAccessException("Professor not found", 1);
        }
        List<Course> courses = professorOptional.get().getCourses();
        courses.sort(Comparator.comparing(Course::getCourseId));
        return courses;
    }
}
