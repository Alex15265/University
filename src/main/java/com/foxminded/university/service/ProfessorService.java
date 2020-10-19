package com.foxminded.university.service;

import com.foxminded.university.dao.ProfessorDAO;
import com.foxminded.university.dao.entities.Course;
import com.foxminded.university.dao.entities.Professor;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

public class ProfessorService {
    private final ClassPathXmlApplicationContext context;
    private final ProfessorDAO professorDAO;

    public ProfessorService(String configLocation, ProfessorDAO professorDAO) {
        context = new ClassPathXmlApplicationContext(configLocation);
        this.professorDAO = professorDAO;
    }

    public Professor createProfessor(String firstName, String lastName) {
        Professor professor = context.getBean("professor", Professor.class);
        professor.setFirstName(firstName);
        professor.setLastName(lastName);
        return professorDAO.create(professor);
    }

    public List<Professor> readAllProfessors() {
        List<Professor> professors = professorDAO.readAll();
        for (Professor professor: professors) {
            professor.setCourses(professorDAO.readCoursesByProfessor(professor.getProfessorId()));
        }
        return professors;
    }

    public Professor readProfessorById(Integer professorId) {
        Professor professor = professorDAO.readByID(professorId);
        professor.setCourses(professorDAO.readCoursesByProfessor(professorId));
        return professor;
    }

    public Professor updateProfessor(Integer professorId, String firstName, String lastName) {
        Professor professor = context.getBean("professor", Professor.class);
        professor.setProfessorId(professorId);
        professor.setFirstName(firstName);
        professor.setLastName(lastName);
        return professorDAO.update(professor);
    }

    public void deleteProfessor(Integer professorId) {
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
