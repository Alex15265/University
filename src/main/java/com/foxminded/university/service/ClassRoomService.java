package com.foxminded.university.service;

import com.foxminded.university.dao.ClassRoomDAO;
import com.foxminded.university.dao.entities.ClassRoom;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;

import java.rmi.NoSuchObjectException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ClassRoomService {
    private final ClassRoomDAO classRoomDAO;

    public ClassRoom create(Integer roomNumber) {
        ClassRoom classRoom = new ClassRoom();
        classRoom.setRoomNumber(roomNumber);
        return classRoomDAO.create(classRoom);
    }

    public List<ClassRoom> readAll() {
        return classRoomDAO.readAll();
    }

    public ClassRoom readByID(Integer classRoomId) throws NoSuchObjectException {
        try {
            return classRoomDAO.readByID(classRoomId);
        } catch (EmptyResultDataAccessException e) {
            throw new NoSuchObjectException("Object not found");
        }
    }

    public ClassRoom update(Integer roomId, Integer roomNumber) throws NoSuchObjectException {
            ClassRoom classRoom = new ClassRoom();
            classRoom.setRoomId(roomId);
            classRoom.setRoomNumber(roomNumber);
            return classRoomDAO.update(classRoom);
    }

    public void delete(Integer roomId) {
        classRoomDAO.delete(roomId);
    }
}
