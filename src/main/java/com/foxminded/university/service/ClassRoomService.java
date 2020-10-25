package com.foxminded.university.service;

import com.foxminded.university.dao.ClassRoomDAO;
import com.foxminded.university.dao.entities.ClassRoom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.util.List;

@Component
public class ClassRoomService {
    private final ClassRoomDAO classRoomDAO;

    @Autowired
    public ClassRoomService(ClassRoomDAO classRoomDAO) {
        this.classRoomDAO = classRoomDAO;
    }

    public ClassRoom create(Integer roomNumber) {
        ClassRoom classRoom = new ClassRoom();
        classRoom.setRoomNumber(roomNumber);
        return classRoomDAO.create(classRoom);
    }

    public List<ClassRoom> readAll() {
        return classRoomDAO.readAll();
    }

    public ClassRoom readByID(Integer classRoomId) throws FileNotFoundException {
        try {
            return classRoomDAO.readByID(classRoomId);
        } catch (EmptyResultDataAccessException e) {
            throw new FileNotFoundException();
        }
    }

    public ClassRoom update(Integer roomId, Integer roomNumber) throws FileNotFoundException {
            ClassRoom classRoom = new ClassRoom();
            classRoom.setRoomId(roomId);
            classRoom.setRoomNumber(roomNumber);
            return classRoomDAO.update(classRoom);
    }

    public void delete(Integer roomId) {
        classRoomDAO.delete(roomId);
    }
}
