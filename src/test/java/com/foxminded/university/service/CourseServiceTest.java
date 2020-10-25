package com.foxminded.university.service;

import com.foxminded.university.dao.CourseDAO;
import com.foxminded.university.dao.entities.Course;
import com.foxminded.university.dao.entities.Student;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.dao.EmptyResultDataAccessException;

import java.io.FileNotFoundException;
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

        when(mockedCourseDAO.create(course)).thenReturn(course);

        assertEquals(course, courseService.create("History", "History Description"));

        verify(mockedCourseDAO, times(1)).create(course);
    }

    @Test
    void readAll() {
        Student student1 = new Student();
        student1.setFirstName("Alex");
        student1.setLastName("Belyaev");
        Student student2 = new Student();
        student2.setFirstName("Lisa");
        student2.setLastName("Ann");
        Student student3 = new Student();
        student3.setFirstName("Ann");
        student3.setLastName("Akimenko");
        Student student4 = new Student();
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
        when(mockedCourseDAO.readStudentsByCourse(1)).thenReturn(students1);
        when(mockedCourseDAO.readStudentsByCourse(2)).thenReturn(students2);

        assertEquals(courses, courseService.readAll());
        assertEquals(course1, courseService.readAll().get(0));
        assertEquals(course2, courseService.readAll().get(1));
        assertEquals(students1, courseService.readAll().get(0).getStudents());
        assertEquals(students2, courseService.readAll().get(1).getStudents());

        verify(mockedCourseDAO, times(5)).readAll();
        verify(mockedCourseDAO, times(5)).readStudentsByCourse(1);
        verify(mockedCourseDAO, times(5)).readStudentsByCourse(2);
    }

    @Test
    void readByID() throws FileNotFoundException {
        Student student1 = new Student();
        student1.setFirstName("Alex");
        student1.setLastName("Belyaev");
        Student student2 = new Student();
        student2.setFirstName("Lisa");
        student2.setLastName("Ann");
        List<Student> students = new ArrayList<>();
        students.add(student1);
        students.add(student2);

        Course course = new Course();
        course.setCourseName("History");
        course.setDescription("History Description");

        when(mockedCourseDAO.readByID(1)).thenReturn(course);
        when(mockedCourseDAO.readStudentsByCourse(1)).thenReturn(students);

        assertEquals(course, courseService.readByID(1));
        assertEquals("History", courseService.readByID(1).getCourseName());
        assertEquals("History Description", courseService.readByID(1).getDescription());
        assertEquals(students, courseService.readByID(1).getStudents());

        verify(mockedCourseDAO, times(4)).readByID(1);
        verify(mockedCourseDAO, times(4)).readStudentsByCourse(1);
    }

    @Test
    void update() throws FileNotFoundException {
        Course course = new Course();
        course.setCourseId(1);
        course.setCourseName("History");
        course.setDescription("History Description");

        when(mockedCourseDAO.update(course)).thenReturn(course);

        assertEquals(course, courseService.update(1, "History", "History Description"));

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

        when(mockedCourseDAO.readStudentsByCourse(1)).thenReturn(students);

        assertEquals(students, courseService.readStudentsByCourse(1));
        assertEquals(student1, courseService.readStudentsByCourse(1).get(0));
        assertEquals(student2, courseService.readStudentsByCourse(1).get(1));

        verify(mockedCourseDAO, times(3)).readStudentsByCourse(1);
    }

    @Test
    void readByID_ShouldThrowExceptionWhenInputIsNonExistentID() {
        when(mockedCourseDAO.readByID(1234)).thenThrow(EmptyResultDataAccessException.class);
        Assertions.assertThrows(FileNotFoundException.class, () ->
                courseService.readByID(1234));
    }

    @Test
    void update_ShouldThrowExceptionWhenInputIsNonExistentID() throws FileNotFoundException {
        Course course = new Course();
        course.setCourseId(1);
        course.setCourseName("History");
        course.setDescription("History Description");
        when(mockedCourseDAO.update(anyObject())).thenThrow(FileNotFoundException.class);
        Assertions.assertThrows(FileNotFoundException.class, () ->
                courseService.update(1, "History", "History Description"));
    }
}