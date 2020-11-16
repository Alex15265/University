package com.foxminded.university.controllers;

import com.foxminded.university.dao.entities.ClassRoom;
import com.foxminded.university.service.ClassRoomService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.rmi.NoSuchObjectException;

@Controller
public class ClassRoomsController {
    private final Logger logger = LoggerFactory.getLogger(ClassRoomsController.class);
    private final ClassRoomService classRoomService;

    @Autowired
    public ClassRoomsController(ClassRoomService classRoomService) {
        this.classRoomService = classRoomService;
    }

    @GetMapping("/classRooms")
    public String showClassRooms(Model model) {
        logger.debug("showing all classrooms");
        model.addAttribute("classrooms", classRoomService.readAll());
        return "classrooms/classrooms";
    }

    @GetMapping("/newClassRoomForm")
    public String showNewClassRoomForm(Model model) {
        logger.debug("showing new ClassRoom form");
        ClassRoom classRoom = new ClassRoom();
        model.addAttribute("classroom", classRoom);
        return "classrooms/new_classroom";
    }

    @PostMapping("/saveClassRoom")
    public String saveClassRoom(@ModelAttribute("classRoom") ClassRoom classRoom) {
        logger.debug("saving new classRoom: {}", classRoom);
        classRoomService.create(classRoom.getRoomNumber());
        return "redirect:/classRooms";
    }

    @GetMapping("/updateClassRoomForm/{id}")
    public String showUpdateClassRoomForm(@PathVariable("id") Integer roomId, Model model) throws NoSuchObjectException {
        logger.debug("showing update classRoom form");
        model.addAttribute("classroom", classRoomService.readByID(roomId));
        return "classrooms/update_classroom";
    }

    @PostMapping("/updateClassRoom/{id}")
    public String update(@ModelAttribute("classRoom") ClassRoom classRoom, @PathVariable("id") Integer roomId) throws NoSuchObjectException {
        logger.debug("updating classRoom with ID: {}", roomId);
        classRoomService.update(roomId, classRoom.getRoomNumber());
        return "redirect:/classRooms";
    }

    @GetMapping("/deleteClassRoom/{id}")
    public String deleteClassRoom(@PathVariable("id") Integer roomId) {
        logger.debug("deleting classRoom with ID: {}", roomId);
        classRoomService.delete(roomId);
        return "redirect:/classRooms";
    }
}
