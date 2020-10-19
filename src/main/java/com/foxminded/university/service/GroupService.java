package com.foxminded.university.service;

import com.foxminded.university.dao.GroupDAO;
import com.foxminded.university.dao.entities.Group;
import com.foxminded.university.dao.entities.Student;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

public class GroupService {
    private final ClassPathXmlApplicationContext context;
    private final GroupDAO groupDAO;

    public GroupService(String configLocation, GroupDAO groupDAO) {
        context = new ClassPathXmlApplicationContext(configLocation);
        this.groupDAO = groupDAO;
    }

    public Group createGroup(String groupName) {
        Group group = context.getBean("group", Group.class);
        group.setGroupName(groupName);
        return groupDAO.create(group);
    }

    public List<Group> readAllGroups() {
        List<Group> groups = groupDAO.readAll();
        for (Group group: groups) {
            group.setStudents(groupDAO.readStudentsByGroup(group.getGroupId()));
        }
        return groups;
    }

    public Group readGroupById(Integer groupId) {
        Group group = groupDAO.readByID(groupId);
        group.setStudents(groupDAO.readStudentsByGroup(groupId));
        return group;
    }

    public Group updateGroup(Integer groupId, String groupName) {
        Group group = context.getBean("group", Group.class);
        group.setGroupId(groupId);
        group.setGroupName(groupName);
        return groupDAO.update(group);
    }

    public void deleteGroup(Integer groupId) {
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
