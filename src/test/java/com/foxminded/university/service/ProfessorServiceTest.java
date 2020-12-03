package com.foxminded.university.service;

import com.foxminded.university.entities.Course;
import com.foxminded.university.entities.Professor;
import com.foxminded.university.repositories.ProfessorRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ProfessorServiceTest {
    ProfessorRepository mockedProfessorRep;
    ProfessorService professorService;

    @BeforeEach
    void init() {
        mockedProfessorRep = mock(ProfessorRepository.class);
        professorService = new ProfessorService(mockedProfessorRep);
    }

    @Test
    void create() {
        Professor professor = new Professor();
        professor.setFirstName("John");
        professor.setLastName("Smith");

        when(mockedProfessorRep.save(professor)).thenReturn(professor);

        assertEquals(professor, professorService.create("John", "Smith"));

        verify(mockedProfessorRep, times(1)).save(professor);
    }

    @Test
    void readAll() {
        List<Course> courses1 = new ArrayList<>();
        List<Course> courses2 = new ArrayList<>();
        Course course1 = new Course();
        course1.setCourseId(1);
        course1.setCourseName("History");
        course1.setDescription("History Description");
        Course course2 = new Course();
        course2.setCourseId(4);
        course2.setCourseName("Robotics");
        course2.setDescription("Robotics Description");

        courses1.add(course1);
        courses1.add(course2);
        courses2.add(course2);

        List<Professor> professors = new ArrayList<>();
        Professor professor1 = new Professor();
        professor1.setProfessorId(1);
        professor1.setFirstName("John");
        professor1.setLastName("Smith");
        professor1.setCourses(courses1);
        Professor professor2 = new Professor();
        professor2.setProfessorId(3);
        professor2.setFirstName("Jade");
        professor2.setLastName("Foster");
        professor2.setCourses(courses2);

        professors.add(professor1);
        professors.add(professor2);

        when(mockedProfessorRep.findByOrderByProfessorIdAsc()).thenReturn(professors);

        assertEquals(professors, professorService.readAll());
        assertEquals(professor1, professorService.readAll().get(0));
        assertEquals(professor2, professorService.readAll().get(1));
        assertEquals(courses1, professorService.readAll().get(0).getCourses());
        assertEquals(courses2, professorService.readAll().get(1).getCourses());

        verify(mockedProfessorRep, times(5)).findByOrderByProfessorIdAsc();
    }

    @Test
    void readById() {
        List<Course> courses = new ArrayList<>();
        Course course1 = new Course();
        course1.setCourseId(1);
        course1.setCourseName("History");
        course1.setDescription("History Description");
        Course course2 = new Course();
        course2.setCourseId(4);
        course2.setCourseName("Robotics");
        course2.setDescription("Robotics Description");

        courses.add(course1);
        courses.add(course2);

        Professor professor = new Professor();
        professor.setProfessorId(3);
        professor.setFirstName("Jade");
        professor.setLastName("Foster");
        professor.setCourses(courses);

        when(mockedProfessorRep.findById(3)).thenReturn(java.util.Optional.of(professor));

        assertEquals(professor, professorService.readById(3));
        assertEquals("Jade", professorService.readById(3).getFirstName());
        assertEquals("Foster", professorService.readById(3).getLastName());
        assertEquals(courses, professorService.readById(3).getCourses());

        verify(mockedProfessorRep, times(4)).findById(3);
    }

    @Test
    void update() {
        Professor professor = new Professor();
        professor.setProfessorId(1);
        professor.setFirstName("Alex");
        professor.setLastName("Smith");

        when(mockedProfessorRep.save(professor)).thenReturn(professor);
        when(mockedProfessorRep.existsById(1)).thenReturn(true);

        assertEquals(professor, professorService.update(1, "Alex", "Smith"));

        verify(mockedProfessorRep, times(1)).save(professor);
    }

    @Test
    void delete() {
        when(mockedProfessorRep.existsById(1)).thenReturn(true);

        professorService.delete(1);

        verify(mockedProfessorRep, times(1)).deleteById(1);
    }

    @Test
    void readCoursesByProfessor() {
        List<Course> courses = new ArrayList<>();
        Course course1 = new Course();
        course1.setCourseId(1);
        course1.setCourseName("History");
        course1.setDescription("History Description");
        Course course2 = new Course();
        course2.setCourseId(4);
        course2.setCourseName("Robotics");
        course2.setDescription("Robotics Description");

        courses.add(course1);
        courses.add(course2);

        Professor professor = new Professor();
        professor.setProfessorId(1);
        professor.setCourses(courses);

        when(mockedProfessorRep.findById(1)).thenReturn(java.util.Optional.of(professor));

        assertEquals(courses, professorService.findCoursesByProfessor(1));
        assertEquals(course1, professorService.findCoursesByProfessor(1).get(0));
        assertEquals(course2, professorService.findCoursesByProfessor(1).get(1));

        verify(mockedProfessorRep, times(3)).findById(1);
    }

    @Test
    void readByID_ShouldThrowExceptionWhenInputIsNonExistentID() {
        when(mockedProfessorRep.findById(1234)).thenThrow(EmptyResultDataAccessException.class);
        Assertions.assertThrows(EmptyResultDataAccessException.class, () ->
                professorService.readById(1234));
    }

    @Test
    void update_ShouldThrowExceptionWhenInputIsNonExistentID() {
        when(mockedProfessorRep.save(anyObject())).thenThrow(EmptyResultDataAccessException.class);
        Assertions.assertThrows(EmptyResultDataAccessException.class, () ->
                professorService.update(234, "Alex", "Belyaev"));
    }
}