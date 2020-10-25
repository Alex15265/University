package com.foxminded.university.service;

import com.foxminded.university.dao.StudentDAO;
import com.foxminded.university.dao.entities.ClassRoom;
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

class StudentServiceTest {
    StudentDAO mockedStudentDAO;
    StudentService studentService;

    @BeforeEach
    void init() {
        mockedStudentDAO = mock(StudentDAO.class);
        studentService = new StudentService(mockedStudentDAO);
    }

    @Test
    void create() {
        Student student = new Student();
        student.setFirstName("Alex");
        student.setLastName("Belyaev");

        when(mockedStudentDAO.create(student)).thenReturn(student);

        assertEquals(student, studentService.create("Alex", "Belyaev"));

        verify(mockedStudentDAO, times(1)).create(student);
    }

    @Test
    void readAll() {
        List<Student> students = new ArrayList<>();
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
        students.add(student1);
        students.add(student2);
        students.add(student3);
        students.add(student4);

        when(mockedStudentDAO.readAll()).thenReturn(students);

        assertEquals(students, studentService.readAll());
        assertEquals(student1, studentService.readAll().get(0));
        assertEquals(student2, studentService.readAll().get(1));
        assertEquals(student3, studentService.readAll().get(2));
        assertEquals(student4, studentService.readAll().get(3));

        verify(mockedStudentDAO, times(5)).readAll();
    }

    @Test
    void readByID() throws FileNotFoundException {
        Student student = new Student();
        student.setFirstName("Ann");
        student.setLastName("Akimenko");

        when(mockedStudentDAO.readByID(1)).thenReturn(student);

        assertEquals(student, studentService.readByID(1));
        assertEquals("Ann", studentService.readByID(1).getFirstName());
        assertEquals("Akimenko", studentService.readByID(1).getLastName());

        verify(mockedStudentDAO, times(3)).readByID(1);
    }

    @Test
    void update() throws FileNotFoundException {
        Student student = new Student();
        student.setStudentId(5);
        student.setFirstName("Ann");
        student.setLastName("Akimenko");

        when(mockedStudentDAO.update(student)).thenReturn(student);

        assertEquals(student, studentService.update(5, "Ann", "Akimenko"));

        verify(mockedStudentDAO, times(1)).update(student);
    }

    @Test
    void delete() {
        studentService.delete(1);

        verify(mockedStudentDAO, times(1)).delete(1);
    }

    @Test
    void readByID_ShouldThrowExceptionWhenInputIsNonExistentID() {
        when(mockedStudentDAO.readByID(1234)).thenThrow(EmptyResultDataAccessException.class);
        Assertions.assertThrows(FileNotFoundException.class, () ->
                studentService.readByID(1234));
    }

    @Test
    void update_ShouldThrowExceptionWhenInputIsNonExistentID() throws FileNotFoundException {
        ClassRoom classRoom = new ClassRoom();
        classRoom.setRoomId(234);
        classRoom.setRoomId(13);
        when(mockedStudentDAO.update(anyObject())).thenThrow(FileNotFoundException.class);
        Assertions.assertThrows(FileNotFoundException.class, () ->
                studentService.update(234, "Alex", "Belyaev"));
    }
}