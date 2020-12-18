package com.foxminded.university.controllers;

import com.foxminded.university.dto.classRoom.ClassRoomDTORequest;
import com.foxminded.university.dto.classRoom.ClassRoomDTOResponse;
import com.foxminded.university.entities.ClassRoom;
import com.foxminded.university.mappers.ClassRoomMapper;
import com.foxminded.university.service.ClassRoomService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class ClassRoomsRestController {
    private final Logger logger = LoggerFactory.getLogger(ClassRoomsRestController.class);
    private final ClassRoomService classRoomService;

    @GetMapping("/api/classRooms")
    public List<ClassRoomDTOResponse> showClassRooms() {
        logger.debug("showing all classrooms");
        List<ClassRoom> classRooms = classRoomService.readAll();
        return classRooms.stream().map(ClassRoomMapper.INSTANCE::classRoomToClassRoomDTOResponse)
                .collect(Collectors.toList());
    }

    @GetMapping("/api/classRooms/{id}")
    public ClassRoomDTOResponse showCourse(@PathVariable("id") Integer roomId) {
        logger.debug("showing classRoom with ID: {}", roomId);
        ClassRoom classRoom = classRoomService.readByID(roomId);
        return ClassRoomMapper.INSTANCE.classRoomToClassRoomDTOResponse(classRoom);
    }

    @PostMapping("/api/classRooms")
    public ClassRoomDTOResponse saveClassRoom(@Valid @RequestBody ClassRoomDTORequest classRoomDTORequest) {
        logger.debug("saving new classroom: {}", classRoomDTORequest);
        ClassRoom classRoom = classRoomService.create(classRoomDTORequest.getRoomNumber());
        return ClassRoomMapper.INSTANCE.classRoomToClassRoomDTOResponse(classRoom);
    }

    @PatchMapping("/api/classRooms/{id}")
    public ClassRoomDTOResponse update(@Valid @RequestBody ClassRoomDTORequest classRoomDTORequest,
                            @PathVariable("id") Integer roomId) {
        logger.debug("updating classRoom with ID: {}", roomId);
        ClassRoom classRoom = classRoomService.update(roomId, classRoomDTORequest.getRoomNumber());
        return ClassRoomMapper.INSTANCE.classRoomToClassRoomDTOResponse(classRoom);
    }

    @DeleteMapping("/api/classRooms/{id}")
    public void deleteClassRoom(@PathVariable("id") Integer roomId) {
        logger.debug("deleting classRoom with ID: {}", roomId);
        classRoomService.delete(roomId);
    }
}
