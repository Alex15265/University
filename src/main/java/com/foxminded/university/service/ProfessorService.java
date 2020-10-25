package com.foxminded.university.service;

import com.foxminded.university.dao.ProfessorDAO;
import com.foxminded.university.dao.entities.Course;
import com.foxminded.university.dao.entities.Professor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.util.List;

@Component
public class ProfessorService {
    private final ProfessorDAO professorDAO;

    @Autowired
    public ProfessorService(ProfessorDAO professorDAO) {
        this.professorDAO = professorDAO;
    }

    public Professor create(String firstName, String lastName) {
        Professor professor = new Professor();
        professor.setFirstName(firstName);
        professor.setLastName(lastName);
        return professorDAO.create(professor);
    }

    public List<Professor> readAll() {
        List<Professor> professors = professorDAO.readAll();
        for (Professor professor: professors) {
            professor.setCourses(professorDAO.findCoursesByProfessor(professor.getProfessorId()));
        }
        return professors;
    }

    public Professor readById(Integer professorId) throws FileNotFoundException {
        try {
        Professor professor = professorDAO.readByID(professorId);
        professor.setCourses(professorDAO.findCoursesByProfessor(professorId));
        return professor;
        } catch (EmptyResultDataAccessException e) {
            throw new FileNotFoundException();
        }
    }

    public Professor update(Integer professorId, String firstName, String lastName) throws FileNotFoundException {
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
