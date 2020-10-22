package com.foxminded.university.service;

import com.foxminded.university.dao.GroupDAO;
import com.foxminded.university.dao.entities.Group;
import com.foxminded.university.dao.entities.Student;

import java.util.List;

public class GroupService {
    private final GroupDAO groupDAO;

    public GroupService(GroupDAO groupDAO) {
        this.groupDAO = groupDAO;
    }

    public Group create(String groupName) {
        Group group = new Group();
        group.setGroupName(groupName);
        return groupDAO.create(group);
    }

    public List<Group> readAll() {
        List<Group> groups = groupDAO.readAll();
        for (Group group: groups) {
            group.setStudents(groupDAO.readStudentsByGroup(group.getGroupId()));
        }
        return groups;
    }

    public Group readById(Integer groupId) {
        Group group = groupDAO.readByID(groupId);
        group.setStudents(groupDAO.readStudentsByGroup(groupId));
        return group;
    }

    public Group update(Integer groupId, String groupName) {
        Group group = new Group();
        group.setGroupId(groupId);
        group.setGroupName(groupName);
        return groupDAO.update(group);
    }

    public void delete(Integer groupId) {
        groupDAO.delete(groupId);
    }

    public void addStudentToGroup(Integer studentId, Integer groupId) {
        groupDAO.addStudentToGroup(studentId, groupId);
    }

    public void deleteStudentFromGroup(Integer studentId) {
        groupDAO.deleteStudentFromGroup(studentId);
    }

    public List<Student> readStudentsByGroup(Integer groupId) {
        return groupDAO.readStudentsByGroup(groupId);
    }
}
