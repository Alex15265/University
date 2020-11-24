package com.foxminded.university.service;

import com.foxminded.university.dao.ProfessorDAO;
import com.foxminded.university.dao.entities.Course;
import com.foxminded.university.dao.entities.Professor;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.rmi.NoSuchObjectException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProfessorService {
    private final ProfessorDAO professorDAO;
    private final Logger logger = LoggerFactory.getLogger(ProfessorService.class);

    public Professor create(String firstName, String lastName) {
        logger.debug("creating professor with firstName: {} and lastName: {}", firstName, lastName);
        Professor professor = new Professor();
        professor.setFirstName(firstName);
        professor.setLastName(lastName);
        return professorDAO.create(professor);
    }

    public List<Professor> readAll() {
        logger.debug("reading all professors");
        return professorDAO.readAll();
    }

    public Professor readById(Integer professorId) throws NoSuchObjectException {
        logger.debug("reading professor with ID: {}", professorId);
        try {
        return professorDAO.readByID(professorId);
        } catch (EmptyResultDataAccessException e) {
            logger.warn("reading professor with ID: {} exception: {}", professorId, e.getMessage());
            throw new NoSuchObjectException("Object not found");
        }
    }

    public Professor update(Integer professorId, String firstName, String lastName) throws NoSuchObjectException {
        logger.debug("updating professor with ID: {}, new firstName: {} and lastName: {}",
                professorId, firstName, lastName);
        Professor professor = new Professor();
        professor.setProfessorId(professorId);
        professor.setFirstName(firstName);
        professor.setLastName(lastName);
        return professorDAO.update(professor);
    }

    public void delete(Integer professorId) {
        logger.debug("deleting professor with ID: {}", professorId);
        professorDAO.delete(professorId);
    }

    public List<Course> findCoursesByProfessor(Integer professorId) {
        return professorDAO.findCoursesByProfessor(professorId);
    }
}
