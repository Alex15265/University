package com.foxminded.university.service;

import com.foxminded.university.entities.ClassRoom;
import com.foxminded.university.repositories.ClassRoomRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ClassRoomServiceTest {
    ClassRoomRepository mockedClassRoomRep;
    ClassRoomService classRoomService;

    @BeforeEach
    void init() {
        mockedClassRoomRep = mock(ClassRoomRepository.class);
        classRoomService = new ClassRoomService(mockedClassRoomRep);
    }

    @Test
    void create() {
        ClassRoom classRoom = new ClassRoom();
        classRoom.setRoomNumber(1);

        when(mockedClassRoomRep.save(classRoom)).thenReturn(classRoom);

        assertEquals(classRoom, classRoomService.create(1));

        verify(mockedClassRoomRep, times(1)).save(classRoom);
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

        when(mockedClassRoomRep.findByOrderByRoomIdAsc()).thenReturn(classRooms);

        assertEquals(classRooms, classRoomService.readAll());
        assertEquals(classRoom1, classRoomService.readAll().get(0));
        assertEquals(classRoom2, classRoomService.readAll().get(1));
        assertEquals(classRoom3, classRoomService.readAll().get(2));

        verify(mockedClassRoomRep, times(4)).findByOrderByRoomIdAsc();
    }

    @Test
    void readByID() {
        ClassRoom classRoom = new ClassRoom();
        classRoom.setRoomNumber(23);

        when(mockedClassRoomRep.findById(1)).thenReturn(java.util.Optional.of(classRoom));

        assertEquals(classRoom, classRoomService.readByID(1));
        assertEquals(23, classRoomService.readByID(1).getRoomNumber());

        verify(mockedClassRoomRep, times(2)).findById(1);
    }

    @Test
    void update() {
        ClassRoom classRoom = new ClassRoom();
        classRoom.setRoomId(1);
        classRoom.setRoomNumber(23);

        when(mockedClassRoomRep.save(classRoom)).thenReturn(classRoom);
        when(mockedClassRoomRep.existsById(classRoom.getRoomId())).thenReturn(true);

        assertEquals(classRoom, classRoomService.update(1, 23));

        verify(mockedClassRoomRep, times(1)).save(classRoom);
        verify(mockedClassRoomRep, times(1)).existsById(classRoom.getRoomId());
    }

    @Test
    void delete() {
        when(mockedClassRoomRep.existsById(1)).thenReturn(true);

        classRoomService.delete(1);

        verify(mockedClassRoomRep, times(1)).deleteById(1);
        verify(mockedClassRoomRep, times(1)).existsById(1);
    }

    @Test
    void create_ShouldThrowExceptionWhenInputClassRoomIsAlreadyExist() {
        ClassRoom classRoom = new ClassRoom();
        classRoom.setRoomNumber(101);
        when(mockedClassRoomRep.findByRoomNumber(101)).thenReturn(classRoom);

        Assertions.assertThrows(IllegalArgumentException.class, () ->
                classRoomService.create(101));
    }
}