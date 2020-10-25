package com.foxminded.university.service;

import com.foxminded.university.dao.GroupDAO;
import com.foxminded.university.dao.entities.Group;
import com.foxminded.university.dao.entities.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.util.List;

@Component
public class GroupService {
    private final GroupDAO groupDAO;

    @Autowired
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
            group.setStudents(groupDAO.findByGroup(group.getGroupId()));
        }
        return groups;
    }

    public Group readById(Integer groupId) throws FileNotFoundException {
        try {
        Group group = groupDAO.readByID(groupId);
        group.setStudents(groupDAO.findByGroup(groupId));
        return group;
        } catch (EmptyResultDataAccessException e) {
            throw new FileNotFoundException();
        }
    }

    public Group update(Integer groupId, String groupName) throws FileNotFoundException {
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

    public List<Student> findByGroup(Integer groupId) {
        return groupDAO.findByGroup(groupId);
    }
}
