package com.foxminded.university.controllers;

import com.foxminded.university.entities.Group;
import com.foxminded.university.entities.Student;
import com.foxminded.university.service.GroupService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class GroupsRestController {
    private final Logger logger = LoggerFactory.getLogger(GroupsRestController.class);
    private final GroupService groupService;

    @GetMapping("/api/groups")
    public List<Group> showGroups() {
        logger.debug("showing all groups");
        return groupService.readAll();
    }

    @GetMapping("/api/groups/{id}")
    public Group showGroup(@PathVariable("id") Integer groupId) {
        logger.debug("showing group with ID: {}", groupId);
        return groupService.readById(groupId);
    }

    @GetMapping("/api/studentsByGroup/{id}")
    public List<Student> showStudentsByGroup(@PathVariable("id") Integer groupId) {
        logger.debug("showing students by group with ID: {}", groupId);
        return groupService.findStudentsByGroup(groupId);
    }

    @GetMapping("/api/saveGroup")
    public Group saveGroup(@RequestParam String groupName) {
        logger.debug("saving new group with groupName: {}", groupName);
        return groupService.create(groupName);
    }

    @GetMapping("/api/updateGroup/{id}")
    public Group update(@RequestParam String groupName, @PathVariable("id") Integer groupId) {
        logger.debug("updating group with ID: {}", groupId);
        return groupService.update(groupId, groupName);
    }

    @GetMapping("/api/deleteGroup/{id}")
    public void deleteGroup(@PathVariable("id") Integer groupId) {
        logger.debug("deleting group with ID: {}", groupId);
        groupService.delete(groupId);
    }
}
