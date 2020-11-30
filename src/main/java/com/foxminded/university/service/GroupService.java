package com.foxminded.university.service;

import com.foxminded.university.entities.Group;
import com.foxminded.university.entities.Student;
import com.foxminded.university.repositories.GroupRepository;
import com.foxminded.university.repositories.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GroupService {
    private final GroupRepository groupRepository;
    private final StudentRepository studentRepository;
    private final Logger logger = LoggerFactory.getLogger(GroupService.class);

    public Group create(String groupName) {
        logger.debug("creating group with groupName: {}", groupName);
        Group group = new Group();
        group.setGroupName(groupName);
        return groupRepository.save(group);
    }

    public List<Group> readAll() {
        logger.debug("reading all groups");
        List<Group> groups = new ArrayList<>();
        groupRepository.findAll().forEach(groups::add);
        groups.sort(Comparator.comparing(Group::getGroupId));
        return groups;
    }

    public Group readById(Integer groupId) {
        logger.debug("reading group with ID: {}", groupId);
        Optional<Group> groupOptional = groupRepository.findById(groupId);
        Group group = new Group();
        if (groupOptional.isPresent()) {
            group = groupOptional.get();
        }
        return group;
    }

    public Group update(Integer groupId, String groupName) {
        logger.debug("updating group with ID: {}, new groupName: {}", groupId, groupName);
        Group group = new Group();
        group.setGroupId(groupId);
        group.setGroupName(groupName);
        return groupRepository.save(group);
    }

    public void delete(Integer groupId) {
        logger.debug("deleting group with ID: {}", groupId);
        groupRepository.deleteById(groupId);
    }

    public void addStudentToGroup(Integer studentId, Integer groupId) {
        logger.debug("adding student with ID: {} to group with ID: {}", studentId, groupId);
        Optional<Group> groupOptional = groupRepository.findById(groupId);
        Group group = new Group();
        if (groupOptional.isPresent()) {
            group = groupOptional.get();
        }
        Optional<Student> studentOptional = studentRepository.findById(studentId);
        Student student = new Student();
        if (studentOptional.isPresent()) {
            student = studentOptional.get();
        }
        group.getStudents().add(student);
        groupRepository.save(group);
    }

    public void deleteStudentFromGroup(Integer studentId, Integer groupId) {
        logger.debug("deleting student with ID: {} from group with ID: {}", studentId, groupId);
        Optional<Group> groupOptional = groupRepository.findById(groupId);
        Group group = new Group();
        if (groupOptional.isPresent()) {
            group = groupOptional.get();
        }
        Optional<Student> studentOptional = studentRepository.findById(studentId);
        Student student = new Student();
        if (studentOptional.isPresent()) {
            student = studentOptional.get();
        }
        group.getStudents().remove(student);
        groupRepository.save(group);
    }

    public List<Student> findStudentsByGroup(Integer groupId) {
        logger.debug("finding student by group with ID: {}", groupId);
        Optional<Group> groupOptional = groupRepository.findById(groupId);
        List<Student> students = new ArrayList<>();
        if (groupOptional.isPresent()) {
            students = groupOptional.get().getStudents();
        }
        students.sort(Comparator.comparing(Student::getStudentId));
        return students;
    }
}
