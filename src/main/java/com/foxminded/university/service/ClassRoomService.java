package com.foxminded.university.service;

import com.foxminded.university.dao.ClassRoomDAO;
import com.foxminded.university.dao.entities.ClassRoom;

import java.util.List;

public class ClassRoomService {
    private final ClassRoomDAO classRoomDAO;

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

    public ClassRoom readByID(Integer classRoomId) {
        return classRoomDAO.readByID(classRoomId);
    }

    public ClassRoom update(Integer roomId, Integer roomNumber) {
        ClassRoom classRoom = new ClassRoom();
        classRoom.setRoomId(roomId);
        classRoom.setRoomNumber(roomNumber);
        return classRoomDAO.update(classRoom);
    }

    public void delete(Integer roomId) {
        classRoomDAO.delete(roomId);
    }
}
