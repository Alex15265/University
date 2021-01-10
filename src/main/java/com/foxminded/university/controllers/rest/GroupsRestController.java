package com.foxminded.university.controllers.rest;

import com.foxminded.university.dto.group.GroupDTORequest;
import com.foxminded.university.dto.group.GroupDTOResponse;
import com.foxminded.university.dto.student.StudentDTOResponse;
import com.foxminded.university.entities.Group;
import com.foxminded.university.entities.Student;
import com.foxminded.university.mappers.GroupMapper;
import com.foxminded.university.mappers.StudentMapper;
import com.foxminded.university.service.GroupService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/groups")
public class GroupsRestController {
    private final Logger logger = LoggerFactory.getLogger(GroupsRestController.class);
    private final GroupService groupService;

    @GetMapping("/")
    @PreAuthorize("hasAuthority('read')")
    @ApiOperation(value = "Method used to fetch all groups")
    public List<GroupDTOResponse> showAllGroups() {
        logger.debug("showing all groups");
        List<Group> groups = groupService.readAll();
        return groups.stream().map(GroupMapper.INSTANCE::groupToGroupDTOResponse).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('read')")
    @ApiOperation(value = "Method used to fetch a group by ID")
    public GroupDTOResponse showGroupByID(
            @ApiParam(value = "ID value of the group you need to retrieve", required = true)
            @PathVariable("id") Integer groupId) {
        logger.debug("showing group with ID: {}", groupId);
        Group group = groupService.readById(groupId);
        return GroupMapper.INSTANCE.groupToGroupDTOResponse(group);
    }

    @GetMapping("/{id}/students")
    @PreAuthorize("hasAuthority('read')")
    @ApiOperation(value = "Method used to show students in a group")
    public List<StudentDTOResponse> showStudentsByGroup(
            @ApiParam(value = "ID value of the group whose students you want to retrieve", required = true)
            @PathVariable("id") Integer groupId) {
        logger.debug("showing students by group with ID: {}", groupId);
        List<Student> students = groupService.findStudentsByGroup(groupId);
        return students.stream().map(StudentMapper.INSTANCE::studentToStudentDTOResponse).collect(Collectors.toList());
    }

    @PostMapping("/")
    @PreAuthorize("hasAuthority('write')")
    @ApiOperation(value = "Method used to save a new group")
    public GroupDTOResponse saveGroup(
            @ApiParam(value = "groupDTORequest for the group you need to save", required = true)
            @Valid @RequestBody GroupDTORequest groupDTORequest) {
        logger.debug("saving new group: {}", groupDTORequest);
        Group group = groupService.create(groupDTORequest.getGroupName());
        return GroupMapper.INSTANCE.groupToGroupDTOResponse(group);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasAuthority('write')")
    @ApiOperation(value = "Method used to update a group by ID")
    public GroupDTOResponse updateGroup(
            @ApiParam(value = "groupDTORequest for the group you need to update", required = true)
            @Valid @RequestBody GroupDTORequest groupDTORequest,
            @ApiParam(value = "ID value of the group you need to update", required = true)
            @PathVariable("id") Integer groupId) {
        logger.debug("updating group with ID: {}", groupId);
        Group group = groupService.update(groupId, groupDTORequest.getGroupName());
        return GroupMapper.INSTANCE.groupToGroupDTOResponse(group);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('write')")
    @ApiOperation(value = "Method used to delete a group by ID")
    public void deleteGroup(
            @ApiParam(value = "ID value of the group you need to delete", required = true)
            @PathVariable("id") Integer groupId) {
        logger.debug("deleting group with ID: {}", groupId);
        groupService.delete(groupId);
    }
}
