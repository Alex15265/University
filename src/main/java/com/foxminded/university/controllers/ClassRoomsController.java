package com.foxminded.university.controllers;

import com.foxminded.university.entities.ClassRoom;
import com.foxminded.university.service.ClassRoomService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class ClassRoomsController {
    private final Logger logger = LoggerFactory.getLogger(ClassRoomsController.class);
    private final ClassRoomService classRoomService;

    @GetMapping("/classRooms")
    @PreAuthorize("hasAuthority('read')")
    public String showClassRooms(Model model) {
        logger.debug("showing all classrooms");
        model.addAttribute("classrooms", classRoomService.readAll());
        return "views/classrooms/classrooms";
    }

    @GetMapping("/newClassRoomForm")
    @PreAuthorize("hasAuthority('write')")
    public String showNewClassRoomForm(Model model) {
        logger.debug("showing new ClassRoom form");
        ClassRoom classRoom = new ClassRoom();
        model.addAttribute("classroom", classRoom);
        return "views/classrooms/new_classroom";
    }

    @PostMapping("/saveClassRoom")
    @PreAuthorize("hasAuthority('write')")
    public String saveClassRoom(@ModelAttribute("classroom") @Valid ClassRoom classRoom, Errors errors) {
        logger.debug("saving new classroom: {}", classRoom);
        if (errors.hasErrors()) {
            return "views/classrooms/new_classroom";
        }
        classRoomService.create(classRoom.getRoomNumber());
        return "redirect:/classRooms";
    }

    @GetMapping("/updateClassRoomForm/{id}")
    @PreAuthorize("hasAuthority('write')")
    public String showUpdateClassRoomForm(@PathVariable("id") Integer roomId, Model model) {
        logger.debug("showing update classRoom form");
        model.addAttribute("classroom", classRoomService.readByID(roomId));
        return "views/classrooms/update_classroom";
    }

    @PostMapping("/updateClassRoom/{id}")
    @PreAuthorize("hasAuthority('write')")
    public String update(@ModelAttribute("classroom") ClassRoom classRoom, @PathVariable("id") Integer roomId) {
        logger.debug("updating classRoom with ID: {}", roomId);
        classRoomService.update(roomId, classRoom.getRoomNumber());
        return "redirect:/classRooms";
    }

    @GetMapping("/deleteClassRoom/{id}")
    @PreAuthorize("hasAuthority('write')")
    public String deleteClassRoom(@PathVariable("id") Integer roomId) {
        logger.debug("deleting classRoom with ID: {}", roomId);
        classRoomService.delete(roomId);
        return "redirect:/classRooms";
    }
}