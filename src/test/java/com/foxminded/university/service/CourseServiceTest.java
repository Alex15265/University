package com.foxminded.university.service;

import com.foxminded.university.entities.Course;
import com.foxminded.university.entities.Professor;
import com.foxminded.university.entities.Student;
import com.foxminded.university.repositories.CourseRepository;
import com.foxminded.university.repositories.ProfessorRepository;
import com.foxminded.university.repositories.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class CourseServiceTest {
    CourseRepository mockedCourseRep;
    ProfessorRepository mockedProfessorRep;
    StudentRepository mockedStudentRep;
    CourseService courseService;

    @BeforeEach
    void init() {
        mockedCourseRep = mock(CourseRepository.class);
        mockedProfessorRep = mock(ProfessorRepository.class);
        mockedStudentRep = mock(StudentRepository.class);
        courseService = new CourseService(mockedCourseRep, mockedProfessorRep, mockedStudentRep);
    }

    @Test
    void create() {
        Course course = new Course();
        course.setCourseName("History");
        course.setDescription("History Description");
        Professor professor = new Professor();
        professor.setProfessorId(1);
        course.setProfessor(professor);

        when(mockedCourseRep.save(course)).thenReturn(course);
        when(mockedProfessorRep.findById(professor.getProfessorId())).thenReturn(java.util.Optional.of(professor));

        assertEquals(course, courseService.create("History", "History Description", 1));

        verify(mockedCourseRep, times(1)).save(course);
        verify(mockedProfessorRep, times(1)).findById(professor.getProfessorId());
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

        when(mockedCourseRep.findByOrderByCourseIdAsc()).thenReturn(courses);

        assertEquals(courses, courseService.readAll());
        assertEquals(course1, courseService.readAll().get(0));
        assertEquals(course2, courseService.readAll().get(1));
        assertEquals(students1, courseService.readAll().get(0).getStudents());
        assertEquals(students2, courseService.readAll().get(1).getStudents());

        verify(mockedCourseRep, times(5)).findByOrderByCourseIdAsc();
    }

    @Test
    void readByID() {
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

        when(mockedCourseRep.findById(1)).thenReturn(java.util.Optional.of(course));

        assertEquals(course, courseService.readByID(1));
        assertEquals(2,courseService.readByID(1).getCourseId());
        assertEquals("History", courseService.readByID(1).getCourseName());
        assertEquals("History Description", courseService.readByID(1).getDescription());
        assertEquals(students, courseService.readByID(1).getStudents());

        verify(mockedCourseRep, times(5)).findById(1);
    }

    @Test
    void update() {
        Course course = new Course();
        course.setCourseId(1);
        course.setCourseName("History");
        course.setDescription("History Description");
        Professor professor = new Professor();
        professor.setProfessorId(1);
        course.setProfessor(professor);

        when(mockedCourseRep.save(course)).thenReturn(course);
        when(mockedCourseRep.existsById(1)).thenReturn(true);
        when(mockedProfessorRep.findById(1)).thenReturn(java.util.Optional.of(professor));

        assertEquals(course, courseService.update(1, "History", "History Description", 1));

        verify(mockedCourseRep, times(1)).save(course);
        verify(mockedCourseRep, times(1)).existsById(1);
        verify(mockedProfessorRep, times(1)).findById(1);
    }

    @Test
    void delete() {
        when(mockedCourseRep.existsById(1)).thenReturn(true);

        courseService.delete(1);

        verify(mockedCourseRep, times(1)).deleteById(1);
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

        Course course = new Course();
        course.setStudents(students);

        when(mockedCourseRep.findById(1)).thenReturn(java.util.Optional.of(course));

        assertEquals(students, courseService.findStudentsByCourse(1));
        assertEquals(student1, courseService.findStudentsByCourse(1).get(0));
        assertEquals(student2, courseService.findStudentsByCourse(1).get(1));

        verify(mockedCourseRep, times(3)).findById(1);
    }
}