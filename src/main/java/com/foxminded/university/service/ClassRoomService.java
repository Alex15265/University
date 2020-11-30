package com.foxminded.university.service;

import com.foxminded.university.entities.ClassRoom;
import com.foxminded.university.repositories.ClassRoomRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClassRoomService {
    private final ClassRoomRepository classRoomRepository;
    private final Logger logger = LoggerFactory.getLogger(ClassRoomService.class);

    public ClassRoom create(Integer roomNumber) {
        logger.debug("creating classroom with roomNumber: {}", roomNumber);
        ClassRoom classRoom = new ClassRoom();
        classRoom.setRoomNumber(roomNumber);
        return classRoomRepository.save(classRoom);
    }

    public List<ClassRoom> readAll() {
        logger.debug("reading all rooms");
        List<ClassRoom> classRooms = new ArrayList<>();
        classRoomRepository.findAll().forEach(classRooms::add);
        classRooms.sort(Comparator.comparing(ClassRoom::getRoomId));
        return classRooms;
    }

    public ClassRoom readByID(Integer classRoomId) {
        logger.debug("reading room with ID: {}", classRoomId);
        Optional<ClassRoom> classRoomOptional = classRoomRepository.findById(classRoomId);
        ClassRoom classRoom = new ClassRoom();
        if (classRoomOptional.isPresent()) {
            classRoom = classRoomOptional.get();
        }
        return classRoom;
    }

    public ClassRoom update(Integer roomId, Integer roomNumber) {
        logger.debug("updating room with ID: {}, new roomNumber: {}", roomId, roomNumber);
        ClassRoom classRoom = new ClassRoom();
        classRoom.setRoomId(roomId);
        classRoom.setRoomNumber(roomNumber);
        return classRoomRepository.save(classRoom);
    }

    public void delete(Integer roomId) {
        logger.debug("deleting room with ID: {}", roomId);
        classRoomRepository.deleteById(roomId);
    }
}
