package com.foxminded.university.controllers;

import com.foxminded.university.dto.group.GroupDTORequest;
import com.foxminded.university.dto.group.GroupDTOResponse;
import com.foxminded.university.dto.student.StudentDTOResponse;
import com.foxminded.university.entities.Group;
import com.foxminded.university.entities.Student;
import com.foxminded.university.mappers.GroupMapper;
import com.foxminded.university.mappers.StudentMapper;
import com.foxminded.university.service.GroupService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class GroupsRestController {
    private final Logger logger = LoggerFactory.getLogger(GroupsRestController.class);
    private final GroupService groupService;

    @GetMapping("/api/groups")
    public List<GroupDTOResponse> showGroups() {
        logger.debug("showing all groups");
        List<Group> groups = groupService.readAll();
        return groups.stream().map(GroupMapper.INSTANCE::groupToGroupDTOResponse).collect(Collectors.toList());
    }

    @GetMapping("/api/groups/{id}")
    public GroupDTOResponse showGroup(@PathVariable("id") Integer groupId) {
        logger.debug("showing group with ID: {}", groupId);
        Group group = groupService.readById(groupId);
        return GroupMapper.INSTANCE.groupToGroupDTOResponse(group);
    }

    @GetMapping("/api/groups/{id}/students")
    public List<StudentDTOResponse> showStudentsByGroup(@PathVariable("id") Integer groupId) {
        logger.debug("showing students by group with ID: {}", groupId);
        List<Student> students = groupService.findStudentsByGroup(groupId);
        return students.stream().map(StudentMapper.INSTANCE::studentToStudentDTOResponse).collect(Collectors.toList());
    }

    @PostMapping("/api/groups")
    public GroupDTOResponse saveGroup(@Valid @RequestBody GroupDTORequest groupDTORequest) {
        logger.debug("saving new group: {}", groupDTORequest);
        Group group = groupService.create(groupDTORequest.getGroupName());
        return GroupMapper.INSTANCE.groupToGroupDTOResponse(group);
    }

    @PatchMapping("/api/groups/{id}")
    public GroupDTOResponse update(@Valid @RequestBody GroupDTORequest groupDTORequest,
                                   @PathVariable("id") Integer groupId) {
        logger.debug("updating group with ID: {}", groupId);
        Group group = groupService.update(groupId, groupDTORequest.getGroupName());
        return GroupMapper.INSTANCE.groupToGroupDTOResponse(group);
    }

    @DeleteMapping("/api/groups/{id}")
    public void deleteGroup(@PathVariable("id") Integer groupId) {
        logger.debug("deleting group with ID: {}", groupId);
        groupService.delete(groupId);
    }
}
