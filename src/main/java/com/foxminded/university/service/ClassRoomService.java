package com.foxminded.university.service;

import com.foxminded.university.dao.ClassRoomDAO;
import com.foxminded.university.dao.entities.ClassRoom;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;

import java.rmi.NoSuchObjectException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ClassRoomService {
    private final ClassRoomDAO classRoomDAO;
    private final Logger logger = LoggerFactory.getLogger(ClassRoomService.class);

    public ClassRoom create(Integer roomNumber) {
        logger.debug("creating classroom with roomNumber: {}", roomNumber);
        ClassRoom classRoom = new ClassRoom();
        classRoom.setRoomNumber(roomNumber);
        return classRoomDAO.create(classRoom);
    }

    public List<ClassRoom> readAll() {
        logger.debug("reading all rooms");
        return classRoomDAO.readAll();
    }

    public ClassRoom readByID(Integer classRoomId) throws NoSuchObjectException {
        logger.debug("reading room with ID: {}", classRoomId);
        try {
            return classRoomDAO.readByID(classRoomId);
        } catch (EmptyResultDataAccessException e) {
            logger.warn("reading room with ID: {} exception: {}", classRoomId, e.getMessage());
            throw new NoSuchObjectException("Object not found");
        }
    }

    public ClassRoom update(Integer roomId, Integer roomNumber) throws NoSuchObjectException {
        logger.debug("updating room with ID: {}, new roomNumber: {}", roomId, roomNumber);
        ClassRoom classRoom = new ClassRoom();
        classRoom.setRoomId(roomId);
        classRoom.setRoomNumber(roomNumber);
        return classRoomDAO.update(classRoom);
    }

    public void delete(Integer roomId) {
        logger.debug("deleting room with ID: {}", roomId);
        classRoomDAO.delete(roomId);
    }
}
