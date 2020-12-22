package com.foxminded.university.controllers.rest;

import com.foxminded.university.dto.classRoom.ClassRoomDTORequest;
import com.foxminded.university.dto.classRoom.ClassRoomDTOResponse;
import com.foxminded.university.entities.ClassRoom;
import com.foxminded.university.mappers.ClassRoomMapper;
import com.foxminded.university.service.ClassRoomService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/classrooms")
public class ClassRoomsRestController {
    private final Logger logger = LoggerFactory.getLogger(ClassRoomsRestController.class);
    private final ClassRoomService classRoomService;

    @GetMapping("/")
    @ApiOperation(value = "Method used to fetch all classrooms")
    public List<ClassRoomDTOResponse> showAllClassRooms() {
        logger.debug("showing all classrooms");
        List<ClassRoom> classRooms = classRoomService.readAll();
        return classRooms.stream().map(ClassRoomMapper.INSTANCE::classRoomToClassRoomDTOResponse)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Method used to fetch a classroom by ID")
    public ClassRoomDTOResponse showClassRoomByID(
            @ApiParam(value = "ID value of the classroom you need to retrieve", required = true)
            @PathVariable("id") Integer roomId) {
        logger.debug("showing classRoom with ID: {}", roomId);
        ClassRoom classRoom = classRoomService.readByID(roomId);
        return ClassRoomMapper.INSTANCE.classRoomToClassRoomDTOResponse(classRoom);
    }

    @PostMapping("/")
    @ApiOperation(value = "Method used to save a new classroom")
    public ClassRoomDTOResponse saveClassRoom(
            @ApiParam(value = "classroomDTORequest for the classroom you need to save", required = true)
            @Valid @RequestBody ClassRoomDTORequest classRoomDTORequest) {
        logger.debug("saving new classroom: {}", classRoomDTORequest);
        ClassRoom classRoom = classRoomService.create(classRoomDTORequest.getRoomNumber());
        return ClassRoomMapper.INSTANCE.classRoomToClassRoomDTOResponse(classRoom);
    }

    @PatchMapping("/{id}")
    @ApiOperation(value = "Method used to update a classroom by ID")
    public ClassRoomDTOResponse updateClassRoom(
            @ApiParam(value = "classroomDTORequest for the classroom you need to update", required = true)
            @Valid @RequestBody ClassRoomDTORequest classRoomDTORequest,
            @ApiParam(value = "ID value of the classroom you need to update", required = true)
            @PathVariable("id") Integer roomId) {
        logger.debug("updating classRoom with ID: {}", roomId);
        ClassRoom classRoom = classRoomService.update(roomId, classRoomDTORequest.getRoomNumber());
        return ClassRoomMapper.INSTANCE.classRoomToClassRoomDTOResponse(classRoom);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Method used to delete a classroom by ID")
    public void deleteClassRoom(
            @ApiParam(value = "ID value of the classroom you need to delete", required = true)
            @PathVariable("id") Integer roomId) {
        logger.debug("deleting classRoom with ID: {}", roomId);
        classRoomService.delete(roomId);
    }
}
