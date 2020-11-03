package com.foxminded.university.service;

import com.foxminded.university.dao.ProfessorDAO;
import com.foxminded.university.dao.entities.Course;
import com.foxminded.university.dao.entities.Professor;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;

import java.rmi.NoSuchObjectException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ProfessorService {
    private final ProfessorDAO professorDAO;

    public Professor create(String firstName, String lastName) {
        Professor professor = new Professor();
        professor.setFirstName(firstName);
        professor.setLastName(lastName);
        return professorDAO.create(professor);
    }

    public List<Professor> readAll() {
        return professorDAO.readAll();
    }

    public Professor readById(Integer professorId) throws NoSuchObjectException {
        try {
        return professorDAO.readByID(professorId);
        } catch (EmptyResultDataAccessException e) {
            throw new NoSuchObjectException("Object not found");
        }
    }

    public Professor update(Integer professorId, String firstName, String lastName) throws NoSuchObjectException {
        Professor professor = new Professor();
        professor.setProfessorId(professorId);
        professor.setFirstName(firstName);
        professor.setLastName(lastName);
        return professorDAO.update(professor);
    }

    public void delete(Integer professorId) {
        professorDAO.delete(professorId);
    }

    public void addCourseToProfessor(Integer courseId, Integer professorId) {
        professorDAO.addCourseToProfessor(courseId, professorId);
    }

    public void deleteCourseFromProfessor(Integer professorId) {
        professorDAO.deleteCourseFromProfessor(professorId);
    }

    public List<Course> findCoursesByProfessor(Integer professorId) {
        return professorDAO.findCoursesByProfessor(professorId);
    }
}
