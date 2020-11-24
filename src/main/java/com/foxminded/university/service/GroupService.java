package com.foxminded.university.service;

import com.foxminded.university.dao.GroupDAO;
import com.foxminded.university.dao.entities.Group;
import com.foxminded.university.dao.entities.Student;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.rmi.NoSuchObjectException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GroupService {
    private final GroupDAO groupDAO;
    private final Logger logger = LoggerFactory.getLogger(GroupService.class);

    public Group create(String groupName) {
        logger.debug("creating group with groupName: {}", groupName);
        Group group = new Group();
        group.setGroupName(groupName);
        return groupDAO.create(group);
    }

    public List<Group> readAll() {
        logger.debug("reading all groups");
        return groupDAO.readAll();
    }

    public Group readById(Integer groupId) throws NoSuchObjectException {
        logger.debug("reading group with ID: {}", groupId);
        try {
        return groupDAO.readByID(groupId);
        } catch (EmptyResultDataAccessException e) {
            logger.warn("reading group with ID: {} exception: {}", groupId, e.getMessage());
            throw new NoSuchObjectException("Object not found");
        }
    }

    public Group update(Integer groupId, String groupName) throws NoSuchObjectException {
        logger.debug("updating group with ID: {}, new groupName: {}", groupId, groupName);
        Group group = new Group();
        group.setGroupId(groupId);
        group.setGroupName(groupName);
        return groupDAO.update(group);
    }

    public void delete(Integer groupId) {
        logger.debug("deleting group with ID: {}", groupId);
        groupDAO.delete(groupId);
    }

    public void addStudentToGroup(Integer studentId, Integer groupId) {
        logger.debug("adding student with ID: {} to group with ID: {}", studentId, groupId);
        groupDAO.addStudentToGroup(studentId, groupId);
    }

    public void deleteStudentFromGroup(Integer studentId, Integer groupId) {
        logger.debug("deleting student with ID: {} from group with ID: {}", studentId, groupId);
        groupDAO.deleteStudentFromGroup(studentId, groupId);
    }

    public List<Student> findStudentsByGroup(Integer groupId) {
        return groupDAO.findStudentsByGroup(groupId);
    }
}
