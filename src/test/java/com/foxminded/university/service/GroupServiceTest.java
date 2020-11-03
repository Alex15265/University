package com.foxminded.university.service;

import com.foxminded.university.dao.GroupDAO;
import com.foxminded.university.dao.entities.ClassRoom;
import com.foxminded.university.dao.entities.Group;
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

class GroupServiceTest {
    GroupDAO mockedGroupDAO;
    GroupService groupService;

    @BeforeEach
    void init() {
        mockedGroupDAO = mock(GroupDAO.class);
        groupService = new GroupService(mockedGroupDAO);
    }

    @Test
    void create() {
        Group group = new Group();
        group.setGroupName("aa-20");

        when(mockedGroupDAO.create(group)).thenReturn(group);

        assertEquals(group, groupService.create("aa-20"));

        verify(mockedGroupDAO, times(1)).create(group);
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

        when(mockedGroupDAO.readAll()).thenReturn(groups);

        assertEquals(groups, groupService.readAll());
        assertEquals(group1, groupService.readAll().get(0));
        assertEquals(group2, groupService.readAll().get(1));
        assertEquals(group3, groupService.readAll().get(2));
        assertEquals(students1, groupService.readAll().get(0).getStudents());
        assertEquals(students2, groupService.readAll().get(1).getStudents());
        assertEquals(students3, groupService.readAll().get(2).getStudents());

        verify(mockedGroupDAO, times(7)).readAll();
    }

    @Test
    void readById() throws NoSuchObjectException {
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

        when(mockedGroupDAO.readByID(1)).thenReturn(group);

        assertEquals(group, groupService.readById(1));
        assertEquals("aa-20", groupService.readById(1).getGroupName());
        assertEquals(students, groupService.readById(1).getStudents());

        verify(mockedGroupDAO, times(3)).readByID(1);
    }

    @Test
    void update() throws NoSuchObjectException {
        Group group = new Group();
        group.setGroupId(7);
        group.setGroupName("bd-17");

        when(mockedGroupDAO.update(group)).thenReturn(group);

        assertEquals(group, groupService.update(7, "bd-17"));

        verify(mockedGroupDAO, times(1)).update(group);
    }

    @Test
    void delete() {
        groupService.delete(1);

        verify(mockedGroupDAO, times(1)).delete(1);
    }

    @Test
    void addStudentToGroup() {
        groupService.addStudentToGroup(1,1);

        verify(mockedGroupDAO, times(1)).addStudentToGroup(1,1);
    }

    @Test
    void deleteStudentFromGroup() {
        groupService.deleteStudentFromGroup(1);

        verify(mockedGroupDAO, times(1)).deleteStudentFromGroup(1);
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

        when(mockedGroupDAO.findByGroup(1)).thenReturn(students);

        assertEquals(students, groupService.findByGroup(1));
        assertEquals(student1, groupService.findByGroup(1).get(0));
        assertEquals(student2, groupService.findByGroup(1).get(1));
        assertEquals(student3, groupService.findByGroup(1).get(2));

        verify(mockedGroupDAO, times(4)).findByGroup(1);

    }

    @Test
    void readByID_ShouldThrowExceptionWhenInputIsNonExistentID() {
        when(mockedGroupDAO.readByID(1234)).thenThrow(EmptyResultDataAccessException.class);
        Assertions.assertThrows(NoSuchObjectException.class, () ->
                groupService.readById(1234));
    }

    @Test
    void update_ShouldThrowExceptionWhenInputIsNonExistentID() throws NoSuchObjectException {
        ClassRoom classRoom = new ClassRoom();
        classRoom.setRoomId(234);
        classRoom.setRoomId(13);
        when(mockedGroupDAO.update(anyObject())).thenThrow(NoSuchObjectException.class);
        Assertions.assertThrows(NoSuchObjectException.class, () ->
                groupService.update(234, "aa-99"));
    }
}