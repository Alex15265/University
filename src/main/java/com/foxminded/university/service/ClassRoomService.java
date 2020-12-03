package com.foxminded.university.service;

import com.foxminded.university.entities.ClassRoom;
import com.foxminded.university.repositories.ClassRoomRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

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
        if (classRoomRepository.findByRoomNumber(roomNumber) != null) {
            throw new IllegalArgumentException("ClassRoom already exist");
        }
        return classRoomRepository.save(classRoom);
    }

    public List<ClassRoom> readAll() {
        logger.debug("reading all rooms");
        return classRoomRepository.findByOrderByRoomIdAsc();
    }

    public ClassRoom readByID(Integer classRoomId) {
        logger.debug("reading room with ID: {}", classRoomId);
        Optional<ClassRoom> classRoomOptional = classRoomRepository.findById(classRoomId);
        if (!classRoomOptional.isPresent()) {
            throw new EmptyResultDataAccessException("ClassRoom not found", 1);
        }
        return classRoomOptional.get();
    }

    public ClassRoom update(Integer roomId, Integer roomNumber) {
        logger.debug("updating room with ID: {}, new roomNumber: {}", roomId, roomNumber);
        if (!classRoomRepository.existsById(roomId)) {
            throw new EmptyResultDataAccessException("ClassRoom not found", 1);
        }
        ClassRoom classRoom = new ClassRoom();
        classRoom.setRoomId(roomId);
        classRoom.setRoomNumber(roomNumber);
        return classRoomRepository.save(classRoom);
    }

    public void delete(Integer roomId) {
        logger.debug("deleting room with ID: {}", roomId);
        if (!classRoomRepository.existsById(roomId)) {
            throw new EmptyResultDataAccessException("ClassRoom not found", 1);
        }
        classRoomRepository.deleteById(roomId);
    }
}
