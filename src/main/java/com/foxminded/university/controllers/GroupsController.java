package com.foxminded.university.controllers;

import com.foxminded.university.dao.entities.Group;
import com.foxminded.university.service.GroupService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.rmi.NoSuchObjectException;

@Controller
@RequiredArgsConstructor
public class GroupsController {
    private final Logger logger = LoggerFactory.getLogger(GroupsController.class);
    private final GroupService groupService;

    @GetMapping("/groups")
    public String showGroups(Model model) {
        logger.debug("showing all groups");
        model.addAttribute("groups", groupService.readAll());
        return "groups/groups";
    }

    @GetMapping("/studentsByGroup/{id}")
    public String showStudentsByGroup(@PathVariable("id") Integer groupId, Model model) {
        logger.debug("showing students by group with ID: {}", groupId);
        model.addAttribute("students", groupService.findStudentsByGroup(groupId));
        return "groups/students_by_group";
    }

    @GetMapping("/newGroupForm")
    public String showNewGroupForm(Model model) {
        logger.debug("showing new group form");
        Group group = new Group();
        model.addAttribute("group", group);
        return "groups/new_group";
    }

    @PostMapping("/saveGroup")
    public String saveGroup(@ModelAttribute("group") Group group) {
        logger.debug("saving new group: {}", group);
        groupService.create(group.getGroupName());
        return "redirect:/groups";
    }

    @GetMapping("/updateGroupForm/{id}")
    public String showUpdateGroupForm(@PathVariable("id") Integer groupId, Model model) throws NoSuchObjectException {
        logger.debug("showing update group form");
        model.addAttribute("group", groupService.readById(groupId));
        return "groups/update_group";
    }

    @PostMapping("/updateGroup/{id}")
    public String update(@ModelAttribute("group") Group group, @PathVariable("id") Integer groupId) throws NoSuchObjectException {
        logger.debug("updating group with ID: {}", groupId);
        groupService.update(groupId, group.getGroupName());
        return "redirect:/groups";
    }

    @GetMapping("/deleteGroup/{id}")
    public String deleteGroup(@PathVariable("id") Integer groupId) {
        logger.debug("deleting group with ID: {}", groupId);
        groupService.delete(groupId);
        return "redirect:/groups";
    }
}