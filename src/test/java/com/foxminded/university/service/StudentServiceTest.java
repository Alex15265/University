package com.foxminded.university.service;

import com.foxminded.university.entities.Group;
import com.foxminded.university.entities.Student;
import com.foxminded.university.repositories.GroupRepository;
import com.foxminded.university.repositories.StudentRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class StudentServiceTest {
    StudentRepository mockedStudentRep;
    GroupRepository mockedGroupRep;
    StudentService studentService;

    @BeforeEach
    void init() {
        mockedStudentRep = mock(StudentRepository.class);
        mockedGroupRep = mock(GroupRepository.class);
        studentService = new StudentService(mockedStudentRep, mockedGroupRep);
    }

    @Test
    void create() {
        Student student = new Student();
        student.setFirstName("Alex");
        student.setLastName("Belyaev");
        Group group = new Group();
        group.setGroupId(1);
        student.setGroup(group);

        when(mockedStudentRep.save(student)).thenReturn(student);
        when(mockedGroupRep.findById(1)).thenReturn(java.util.Optional.of(group));

        assertEquals(student, studentService.create("Alex", "Belyaev", 1));

        verify(mockedStudentRep, times(1)).save(student);
        verify(mockedGroupRep, times(1)).findById(1);
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

        when(mockedStudentRep.findByOrderByStudentIdAsc()).thenReturn(students);

        assertEquals(students, studentService.readAll());
        assertEquals(student1, studentService.readAll().get(0));
        assertEquals(student2, studentService.readAll().get(1));
        assertEquals(student3, studentService.readAll().get(2));
        assertEquals(student4, studentService.readAll().get(3));

        verify(mockedStudentRep, times(5)).findByOrderByStudentIdAsc();
    }

    @Test
    void readByID() {
        Student student = new Student();
        student.setFirstName("Ann");
        student.setLastName("Akimenko");

        when(mockedStudentRep.findById(1)).thenReturn(java.util.Optional.of(student));

        assertEquals(student, studentService.readByID(1));
        assertEquals("Ann", studentService.readByID(1).getFirstName());
        assertEquals("Akimenko", studentService.readByID(1).getLastName());

        verify(mockedStudentRep, times(3)).findById(1);
    }

    @Test
    void update() {
        Student student = new Student();
        student.setStudentId(5);
        student.setFirstName("Ann");
        student.setLastName("Akimenko");

        Group group = new Group();
        group.setGroupId(2);

        student.setGroup(group);

        when(mockedStudentRep.save(student)).thenReturn(student);
        when(mockedStudentRep.existsById(5)).thenReturn(true);
        when(mockedGroupRep.findById(2)).thenReturn(java.util.Optional.of(group));


        assertEquals(student, studentService.update(5, "Ann", "Akimenko", 2));

        verify(mockedStudentRep, times(1)).save(student);
    }

    @Test
    void delete() {
        when(mockedStudentRep.existsById(1)).thenReturn(true);

        studentService.delete(1);

        verify(mockedStudentRep, times(1)).deleteById(1);
    }

    @Test
    void readByID_ShouldThrowExceptionWhenInputIsNonExistentID() {
        when(mockedStudentRep.findById(1234)).thenThrow(EmptyResultDataAccessException.class);
        Assertions.assertThrows(EmptyResultDataAccessException.class, () ->
                studentService.readByID(1234));
    }

    @Test
    void update_ShouldThrowExceptionWhenInputIsNonExistentID() {
        when(mockedStudentRep.save(anyObject())).thenThrow(EmptyResultDataAccessException.class);
        Assertions.assertThrows(EmptyResultDataAccessException.class, () ->
                studentService.update(234, "Alex", "Belyaev", 1));
    }
}