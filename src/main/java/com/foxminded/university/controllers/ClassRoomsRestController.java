package com.foxminded.university.controllers;

import com.foxminded.university.entities.ClassRoom;
import com.foxminded.university.service.ClassRoomService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ClassRoomsRestController {
    private final Logger logger = LoggerFactory.getLogger(ClassRoomsRestController.class);
    private final ClassRoomService classRoomService;

    @GetMapping("/api/classRooms")
    public List<ClassRoom> showClassRooms() {
        logger.debug("showing all classrooms");
        return classRoomService.readAll();
    }

    @GetMapping("/api/classRooms/{id}")
    public ClassRoom showCourse(@PathVariable("id") Integer roomId) {
        logger.debug("showing classRoom with ID: {}", roomId);
        return classRoomService.readByID(roomId);
    }

    @PostMapping("/api/classRooms")
    public ClassRoom saveClassRoom(@RequestParam Integer roomNumber) {
        logger.debug("saving new classroom with roomNumber: {}", roomNumber);
        return classRoomService.create(roomNumber);
    }

    @PatchMapping("/api/classRooms/{id}")
    public ClassRoom update(@RequestParam Integer roomNumber, @PathVariable("id") Integer roomId) {
        logger.debug("updating classRoom with ID: {}", roomId);
        return classRoomService.update(roomId, roomNumber);
    }

    @DeleteMapping("/api/classRooms/{id}")
    public void deleteClassRoom(@PathVariable("id") Integer roomId) {
        logger.debug("deleting classRoom with ID: {}", roomId);
        classRoomService.delete(roomId);
    }
}
