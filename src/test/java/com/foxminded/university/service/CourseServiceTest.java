package com.foxminded.university.service;

import com.foxminded.university.dao.CourseDAO;
import com.foxminded.university.dao.entities.Course;
import com.foxminded.university.dao.entities.Student;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.dao.EmptyResultDataAccessException;

import java.rmi.NoSuchObjectException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class CourseServiceTest {
    CourseDAO mockedCourseDAO;
    CourseService courseService;

    @BeforeEach
    void init() {
        mockedCourseDAO = mock(CourseDAO.class);
        courseService = new CourseService(mockedCourseDAO);
    }

    @Test
    void create() {
        Course course = new Course();
        course.setCourseName("History");
        course.setDescription("History Description");
        course.setProfessorId(1);

        when(mockedCourseDAO.create(course)).thenReturn(course);

        assertEquals(course, courseService.create("History", "History Description", 1));

        verify(mockedCourseDAO, times(1)).create(course);
    }

    @Test
    void readAll() {
        Student student1 = new Student();
        student1.setStudentId(1);
        student1.setFirstName("Alex");
        student1.setLastName("Belyaev");
        Student student2 = new Student();
        student2.setStudentId(3);
        student2.setFirstName("Lisa");
        student2.setLastName("Ann");
        Student student3 = new Student();
        student3.setStudentId(7);
        student3.setFirstName("Ann");
        student3.setLastName("Akimenko");
        Student student4 = new Student();
        student4.setStudentId(9);
        student4.setFirstName("John");
        student4.setLastName("Smith");

        List<Student> students1 = new ArrayList<>();
        students1.add(student1);
        students1.add(student2);

        List<Student> students2 = new ArrayList<>();
        students1.add(student1);
        students1.add(student2);
        students1.add(student3);
        students1.add(student4);

        List<Course> courses = new ArrayList<>();
        Course course1 = new Course();
        course1.setCourseId(1);
        course1.setCourseName("History");
        course1.setDescription("History Description");
        course1.setStudents(students1);
        Course course2 = new Course();
        course2.setCourseId(2);
        course2.setCourseName("Robotics");
        course2.setDescription("Robotics Description");
        course2.setStudents(students2);

        courses.add(course1);
        courses.add(course2);

        when(mockedCourseDAO.readAll()).thenReturn(courses);

        assertEquals(courses, courseService.readAll());
        assertEquals(course1, courseService.readAll().get(0));
        assertEquals(course2, courseService.readAll().get(1));
        assertEquals(students1, courseService.readAll().get(0).getStudents());
        assertEquals(students2, courseService.readAll().get(1).getStudents());

        verify(mockedCourseDAO, times(5)).readAll();
    }

    @Test
    void readByID() throws NoSuchObjectException {
        Student student1 = new Student();
        student1.setStudentId(1);
        student1.setFirstName("Alex");
        student1.setLastName("Belyaev");
        Student student2 = new Student();
        student2.setStudentId(3);
        student2.setFirstName("Lisa");
        student2.setLastName("Ann");
        List<Student> students = new ArrayList<>();
        students.add(student1);
        students.add(student2);

        Course course = new Course();
        course.setCourseId(2);
        course.setCourseName("History");
        course.setDescription("History Description");
        course.setStudents(students);

        when(mockedCourseDAO.readByID(1)).thenReturn(course);

        assertEquals(course, courseService.readByID(1));
        assertEquals(2,courseService.readByID(1).getCourseId());
        assertEquals("History", courseService.readByID(1).getCourseName());
        assertEquals("History Description", courseService.readByID(1).getDescription());
        assertEquals(students, courseService.readByID(1).getStudents());

        verify(mockedCourseDAO, times(5)).readByID(1);
    }

    @Test
    void update() throws NoSuchObjectException {
        Course course = new Course();
        course.setCourseId(1);
        course.setCourseName("History");
        course.setDescription("History Description");
        course.setProfessorId(1);

        when(mockedCourseDAO.update(course)).thenReturn(course);

        assertEquals(course, courseService.update(1, "History", "History Description", 1));

        verify(mockedCourseDAO, times(1)).update(course);
    }

    @Test
    void delete() {
        courseService.delete(1);

        verify(mockedCourseDAO, times(1)).delete(1);
    }

    @Test
    void addStudentToCourse() {
        courseService.addStudentToCourse(1,1);

        verify(mockedCourseDAO, times(1)).addStudentToCourse(1,1);
    }

    @Test
    void deleteStudentFromCourse() {
        courseService.deleteStudentFromCourse(1,1);

        verify(mockedCourseDAO, times(1)).deleteStudentFromCourse(1,1);
    }

    @Test
    void readStudentsByCourse() {
        List<Student> students = new ArrayList<>();
        Student student1 = new Student();
        student1.setStudentId(3);
        student1.setFirstName("Lisa");
        student1.setLastName("Ann");
        Student student2 = new Student();
        student2.setStudentId(7);
        student2.setFirstName("Ann");
        student2.setLastName("Akimenko");

        students.add(student1);
        students.add(student2);

        when(mockedCourseDAO.findByCourse(1)).thenReturn(students);

        assertEquals(students, courseService.findByCourse(1));
        assertEquals(student1, courseService.findByCourse(1).get(0));
        assertEquals(student2, courseService.findByCourse(1).get(1));

        verify(mockedCourseDAO, times(3)).findByCourse(1);
    }

    @Test
    void readByID_ShouldThrowExceptionWhenInputIsNonExistentID() {
        when(mockedCourseDAO.readByID(1234)).thenThrow(EmptyResultDataAccessException.class);
        Assertions.assertThrows(NoSuchObjectException.class, () ->
                courseService.readByID(1234));
    }

    @Test
    void update_ShouldThrowExceptionWhenInputIsNonExistentID() throws NoSuchObjectException {
        when(mockedCourseDAO.update(anyObject())).thenThrow(NoSuchObjectException.class);
        Assertions.assertThrows(NoSuchObjectException.class, () ->
                courseService.update(1, "History", "History Description", 1));
    }
}