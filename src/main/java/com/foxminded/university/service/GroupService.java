package com.foxminded.university.service;

import com.foxminded.university.dao.GroupDAO;
import com.foxminded.university.dao.entities.Group;
import com.foxminded.university.dao.entities.Student;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;

import java.rmi.NoSuchObjectException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class GroupService {
    private final GroupDAO groupDAO;

    public Group create(String groupName) {
        Group group = new Group();
        group.setGroupName(groupName);
        return groupDAO.create(group);
    }

    public List<Group> readAll() {
        return groupDAO.readAll();
    }

    public Group readById(Integer groupId) throws NoSuchObjectException {
        try {
        return groupDAO.readByID(groupId);
        } catch (EmptyResultDataAccessException e) {
            throw new NoSuchObjectException("Object not found");
        }
    }

    public Group update(Integer groupId, String groupName) throws NoSuchObjectException {
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
