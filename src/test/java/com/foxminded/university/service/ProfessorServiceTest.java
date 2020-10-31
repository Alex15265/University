package com.foxminded.university.service;

import com.foxminded.university.dao.ProfessorDAO;
import com.foxminded.university.dao.entities.ClassRoom;
import com.foxminded.university.dao.entities.Course;
import com.foxminded.university.dao.entities.Professor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.dao.EmptyResultDataAccessException;

import java.rmi.NoSuchObjectException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ProfessorServiceTest {
    ProfessorDAO mockedProfessorDAO;
    ProfessorService professorService;

    @BeforeEach
    void init() {
        mockedProfessorDAO = mock(ProfessorDAO.class);
        professorService = new ProfessorService(mockedProfessorDAO);
    }

    @Test
    void create() {
        Professor professor = new Professor();
        professor.setFirstName("John");
        professor.setLastName("Smith");

        when(mockedProfessorDAO.create(professor)).thenReturn(professor);

        assertEquals(professor, professorService.create("John", "Smith"));

        verify(mockedProfessorDAO, times(1)).create(professor);
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

        when(mockedProfessorDAO.readAll()).thenReturn(professors);
        when(mockedProfessorDAO.findCoursesByProfessor(1)).thenReturn(courses1);
        when(mockedProfessorDAO.findCoursesByProfessor(3)).thenReturn(courses2);

        assertEquals(professors, professorService.readAll());
        assertEquals(professor1, professorService.readAll().get(0));
        assertEquals(professor2, professorService.readAll().get(1));
        assertEquals(courses1, professorService.readAll().get(0).getCourses());
        assertEquals(courses2, professorService.readAll().get(1).getCourses());

        verify(mockedProfessorDAO, times(5)).readAll();
        verify(mockedProfessorDAO, times(5)).findCoursesByProfessor(1);
        verify(mockedProfessorDAO, times(5)).findCoursesByProfessor(3);
    }

    @Test
    void readById() throws NoSuchObjectException {
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

        when(mockedProfessorDAO.readByID(3)).thenReturn(professor);
        when(mockedProfessorDAO.findCoursesByProfessor(3)).thenReturn(courses);

        assertEquals(professor, professorService.readById(3));
        assertEquals("Jade", professorService.readById(3).getFirstName());
        assertEquals("Foster", professorService.readById(3).getLastName());
        assertEquals(courses, professorService.readById(3).getCourses());

        verify(mockedProfessorDAO, times(4)).readByID(3);
        verify(mockedProfessorDAO, times(4)).findCoursesByProfessor(3);
    }

    @Test
    void update() throws NoSuchObjectException {
        Professor professor = new Professor();
        professor.setProfessorId(1);
        professor.setFirstName("Alex");
        professor.setLastName("Smith");

        when(mockedProfessorDAO.update(professor)).thenReturn(professor);

        assertEquals(professor, professorService.update(1, "Alex", "Smith"));

        verify(mockedProfessorDAO, times(1)).update(professor);
    }

    @Test
    void delete() {
        professorService.delete(1);

        verify(mockedProfessorDAO, times(1)).delete(1);
    }

    @Test
    void addCourseToProfessor() {
        professorService.addCourseToProfessor(1,1);

        verify(mockedProfessorDAO, times(1)).addCourseToProfessor(1,1);
    }

    @Test
    void deleteCourseFromProfessor() {
        professorService.deleteCourseFromProfessor(1);

        verify(mockedProfessorDAO, times(1)).deleteCourseFromProfessor(1);
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

        when(mockedProfessorDAO.findCoursesByProfessor(1)).thenReturn(courses);

        assertEquals(courses, professorService.findCoursesByProfessor(1));
        assertEquals(course1, professorService.findCoursesByProfessor(1).get(0));
        assertEquals(course2, professorService.findCoursesByProfessor(1).get(1));

        verify(mockedProfessorDAO, times(3)).findCoursesByProfessor(1);
    }

    @Test
    void readByID_ShouldThrowExceptionWhenInputIsNonExistentID() {
        when(mockedProfessorDAO.readByID(1234)).thenThrow(EmptyResultDataAccessException.class);
        Assertions.assertThrows(NoSuchObjectException.class, () ->
                professorService.readById(1234));
    }

    @Test
    void update_ShouldThrowExceptionWhenInputIsNonExistentID() throws NoSuchObjectException {
        ClassRoom classRoom = new ClassRoom();
        classRoom.setRoomId(234);
        classRoom.setRoomId(13);
        when(mockedProfessorDAO.update(anyObject())).thenThrow(NoSuchObjectException.class);
        Assertions.assertThrows(NoSuchObjectException.class, () ->
                professorService.update(234, "Alex", "Belyaev"));
    }
}