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

class GroupServiceTest {
    GroupRepository mockedGroupRep;
    StudentRepository mockedStudentRep;
    GroupService groupService;

    @BeforeEach
    void init() {
        mockedGroupRep = mock(GroupRepository.class);
        mockedStudentRep = mock(StudentRepository.class);
        groupService = new GroupService(mockedGroupRep, mockedStudentRep);
    }

    @Test
    void create() {
        Group group = new Group();
        group.setGroupName("bu-20");

        when(mockedGroupRep.save(group)).thenReturn(group);

        assertEquals(group, groupService.create("bu-20"));

        verify(mockedGroupRep, times(1)).save(group);
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

        List<Student> students3 = new ArrayList<>();
        students3.add(student1);
        students3.add(student2);
        students3.add(student3);

        List<Group> groups = new ArrayList<>();
        Group group1 = new Group();
        group1.setGroupId(3);
        group1.setGroupName("aa-20");
        group1.setStudents(students1);
        Group group2 = new Group();
        group2.setGroupId(6);
        group2.setGroupName("ab-22");
        group2.setStudents(students2);
        Group group3 = new Group();
        group3.setGroupId(7);
        group3.setGroupName("bd-17");
        group3.setStudents(students3);


        groups.add(group1);
        groups.add(group2);
        groups.add(group3);

        when(mockedGroupRep.findByOrderByGroupIdAsc()).thenReturn(groups);

        assertEquals(groups, groupService.readAll());
        assertEquals(group1, groupService.readAll().get(0));
        assertEquals(group2, groupService.readAll().get(1));
        assertEquals(group3, groupService.readAll().get(2));
        assertEquals(students1, groupService.readAll().get(0).getStudents());
        assertEquals(students2, groupService.readAll().get(1).getStudents());
        assertEquals(students3, groupService.readAll().get(2).getStudents());

        verify(mockedGroupRep, times(7)).findByOrderByGroupIdAsc();
    }

    @Test
    void readById() {
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

        Group group = new Group();
        group.setGroupName("aa-20");
        group.setStudents(students);

        when(mockedGroupRep.findById(1)).thenReturn(java.util.Optional.of(group));

        assertEquals(group, groupService.readById(1));
        assertEquals("aa-20", groupService.readById(1).getGroupName());
        assertEquals(students, groupService.readById(1).getStudents());

        verify(mockedGroupRep, times(3)).findById(1);
    }

    @Test
    void update() {
        Group group = new Group();
        group.setGroupId(7);
        group.setGroupName("bd-17");

        when(mockedGroupRep.save(group)).thenReturn(group);
        when(mockedGroupRep.existsById(7)).thenReturn(true);

        assertEquals(group, groupService.update(7, "bd-17"));

        verify(mockedGroupRep, times(1)).save(group);
    }

    @Test
    void delete() {
        when(mockedGroupRep.existsById(1)).thenReturn(true);

        groupService.delete(1);

        verify(mockedGroupRep, times(1)).deleteById(1);
    }

    @Test
    void readStudentsByGroup() {
        List<Student> students = new ArrayList<>();
        Student student1 = new Student();
        student1.setStudentId(3);
        student1.setFirstName("Lisa");
        student1.setLastName("Ann");
        Student student2 = new Student();
        student2.setStudentId(7);
        student2.setFirstName("Ann");
        student2.setLastName("Akimenko");
        Student student3 = new Student();
        student3.setStudentId(11);
        student3.setFirstName("Alex");
        student3.setLastName("Belyaev");

        students.add(student1);
        students.add(student2);
        students.add(student3);

        Group group = new Group();
        group.setStudents(students);

        when(mockedGroupRep.findById(1)).thenReturn(java.util.Optional.of(group));

        assertEquals(students, groupService.findStudentsByGroup(1));
        assertEquals(student1, groupService.findStudentsByGroup(1).get(0));
        assertEquals(student2, groupService.findStudentsByGroup(1).get(1));
        assertEquals(student3, groupService.findStudentsByGroup(1).get(2));

        verify(mockedGroupRep, times(4)).findById(1);

    }

    @Test
    void readByID_ShouldThrowExceptionWhenInputIsNonExistentID() {
        when(mockedGroupRep.findById(1234)).thenThrow(EmptyResultDataAccessException.class);
        Assertions.assertThrows(EmptyResultDataAccessException.class, () ->
                groupService.readById(1234));
    }

    @Test
    void update_ShouldThrowExceptionWhenInputIsNonExistentID() {
        when(mockedGroupRep.save(anyObject())).thenThrow(EmptyResultDataAccessException.class);
        Assertions.assertThrows(EmptyResultDataAccessException.class, () ->
                groupService.update(234, "aa-99"));
    }
}