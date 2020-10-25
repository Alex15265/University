package com.foxminded.university.service;

import com.foxminded.university.dao.ClassRoomDAO;
import com.foxminded.university.dao.entities.ClassRoom;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.dao.EmptyResultDataAccessException;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ClassRoomServiceTest {
    ClassRoomDAO mockedClassRoomDAO;
    ClassRoomService classRoomService;

    @BeforeEach
    void init() {
        mockedClassRoomDAO = mock(ClassRoomDAO.class);
        classRoomService = new ClassRoomService(mockedClassRoomDAO);
    }

    @Test
    void create() {
        ClassRoom classRoom = new ClassRoom();
        classRoom.setRoomNumber(1);

        when(mockedClassRoomDAO.create(classRoom)).thenReturn(classRoom);

        assertEquals(classRoom, classRoomService.create(1));

        verify(mockedClassRoomDAO, times(1)).create(classRoom);
    }

    @Test
    void readAll() {
        List<ClassRoom> classRooms = new ArrayList<>();
        ClassRoom classRoom1 = new ClassRoom();
        classRoom1.setRoomNumber(1);
        ClassRoom classRoom2 = new ClassRoom();
        classRoom2.setRoomNumber(2);
        ClassRoom classRoom3 = new ClassRoom();
        classRoom3.setRoomNumber(3);
        classRooms.add(classRoom1);
        classRooms.add(classRoom2);
        classRooms.add(classRoom3);

        when(mockedClassRoomDAO.readAll()).thenReturn(classRooms);

        assertEquals(classRooms, classRoomService.readAll());
        assertEquals(classRoom1, classRoomService.readAll().get(0));
        assertEquals(classRoom2, classRoomService.readAll().get(1));
        assertEquals(classRoom3, classRoomService.readAll().get(2));

        verify(mockedClassRoomDAO, times(4)).readAll();
    }

    @Test
    void readByID() throws FileNotFoundException {
        ClassRoom classRoom = new ClassRoom();
        classRoom.setRoomNumber(23);

        when(mockedClassRoomDAO.readByID(1)).thenReturn(classRoom);

        assertEquals(classRoom, classRoomService.readByID(1));
        assertEquals(23, classRoomService.readByID(1).getRoomNumber());

        verify(mockedClassRoomDAO, times(2)).readByID(1);
    }

    @Test
    void update() throws FileNotFoundException {
        ClassRoom classRoom = new ClassRoom();
        classRoom.setRoomId(1);
        classRoom.setRoomNumber(23);

        when(mockedClassRoomDAO.update(classRoom)).thenReturn(classRoom);

        assertEquals(classRoom, classRoomService.update(1, 23));

        verify(mockedClassRoomDAO, times(1)).update(classRoom);
    }

    @Test
    void delete() {
        classRoomService.delete(1);

        verify(mockedClassRoomDAO, times(1)).delete(1);
    }

    @Test
    void readByID_ShouldThrowExceptionWhenInputIsNonExistentID() {
        when(mockedClassRoomDAO.readByID(1234)).thenThrow(EmptyResultDataAccessException.class);
        Assertions.assertThrows(FileNotFoundException.class, () ->
                classRoomService.readByID(1234));
    }

    @Test
    void update_ShouldThrowExceptionWhenInputIsNonExistentID() throws FileNotFoundException {
        ClassRoom classRoom = new ClassRoom();
        classRoom.setRoomId(234);
        classRoom.setRoomId(13);
        when(mockedClassRoomDAO.update(anyObject())).thenThrow(FileNotFoundException.class);
        Assertions.assertThrows(FileNotFoundException.class, () ->
                classRoomService.update(234, 13));
    }
}