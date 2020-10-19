package com.foxminded.university.service;

import com.foxminded.university.dao.ClassRoomDAO;
import com.foxminded.university.dao.entities.ClassRoom;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

public class ClassRoomService {
    private final ClassPathXmlApplicationContext context;
    private final ClassRoomDAO classRoomDAO;

    public ClassRoomService(String configLocation, ClassRoomDAO classRoomDAO) {
        context = new ClassPathXmlApplicationContext(configLocation);
        this.classRoomDAO = classRoomDAO;
    }

    public ClassRoom createClassRoom(Integer roomNumber) {
        ClassRoom classRoom = context.getBean("classRoom", ClassRoom.class);
        classRoom.setRoomNumber(roomNumber);
        return classRoomDAO.create(classRoom);
    }

    public List<ClassRoom> readAllClassRooms() {
        return classRoomDAO.readAll();
    }

    public ClassRoom readClassRoomByID(Integer classRoomId) {
        return classRoomDAO.readByID(classRoomId);
    }

    public ClassRoom updateClassRoom(Integer roomId, Integer roomNumber) {
        ClassRoom classRoom = context.getBean("classRoom", ClassRoom.class);
        classRoom.setRoomId(roomId);
        classRoom.setRoomNumber(roomNumber);
        return classRoomDAO.update(classRoom);
    }

    public void deleteClassRoom(Integer roomId) {
        classRoomDAO.delete(roomId);
    }
}
