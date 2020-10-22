package com.foxminded.university.service;

import com.foxminded.university.dao.ProfessorDAO;
import com.foxminded.university.dao.entities.Course;
import com.foxminded.university.dao.entities.Professor;

import java.util.List;

public class ProfessorService {
    private final ProfessorDAO professorDAO;

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
            professor.setCourses(professorDAO.readCoursesByProfessor(professor.getProfessorId()));
        }
        return professors;
    }

    public Professor readById(Integer professorId) {
        Professor professor = professorDAO.readByID(professorId);
        professor.setCourses(professorDAO.readCoursesByProfessor(professorId));
        return professor;
    }

    public Professor update(Integer professorId, String firstName, String lastName) {
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

    public List<Course> readCoursesByProfessor(Integer professorId) {
        return professorDAO.readCoursesByProfessor(professorId);
    }
}
