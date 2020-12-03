package com.foxminded.university.service;

import com.foxminded.university.entities.Group;
import com.foxminded.university.entities.Student;
import com.foxminded.university.repositories.GroupRepository;
import com.foxminded.university.repositories.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

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
        if (groupRepository.findByGroupName(groupName) != null) {
            throw new IllegalArgumentException("Group already exist");
        }
        return groupRepository.save(group);
    }

    public List<Group> readAll() {
        logger.debug("reading all groups");
        return groupRepository.findByOrderByGroupIdAsc();
    }

    public Group readById(Integer groupId) {
        logger.debug("reading group with ID: {}", groupId);
        Optional<Group> groupOptional = groupRepository.findById(groupId);
        if (!groupOptional.isPresent()) {
            throw new EmptyResultDataAccessException("Group not found", 1);
        }
        return groupOptional.get();
    }

    public Group update(Integer groupId, String groupName) {
        logger.debug("updating group with ID: {}, new groupName: {}", groupId, groupName);
        if (!groupRepository.existsById(groupId)) {
            throw new EmptyResultDataAccessException("Group not found", 1);
        }
        Group group = new Group();
        group.setGroupId(groupId);
        group.setGroupName(groupName);
        return groupRepository.save(group);
    }

    public void delete(Integer groupId) {
        logger.debug("deleting group with ID: {}", groupId);
        if (!groupRepository.existsById(groupId)) {
            throw new EmptyResultDataAccessException("Group not found", 1);
        }
        groupRepository.deleteById(groupId);
    }

    public void addStudentToGroup(Integer studentId, Integer groupId) {
        logger.debug("adding student with ID: {} to group with ID: {}", studentId, groupId);
        Optional<Group> groupOptional = groupRepository.findById(groupId);
        if (!groupOptional.isPresent()) {
            throw new EmptyResultDataAccessException("Group not found", 1);
        }
        Group group = groupOptional.get();
        Optional<Student> studentOptional = studentRepository.findById(studentId);
        if (!studentOptional.isPresent()) {
            throw new EmptyResultDataAccessException("Student not found", 1);
        }
        Student student = studentOptional.get();
        group.getStudents().add(student);
        groupRepository.save(group);
    }

    public void deleteStudentFromGroup(Integer studentId, Integer groupId) {
        logger.debug("deleting student with ID: {} from group with ID: {}", studentId, groupId);
        Optional<Group> groupOptional = groupRepository.findById(groupId);
        if (!groupOptional.isPresent()) {
            throw new EmptyResultDataAccessException("Group not found", 1);
        }
        Group group = groupOptional.get();
        Optional<Student> studentOptional = studentRepository.findById(studentId);
        if (!studentOptional.isPresent()) {
            throw new EmptyResultDataAccessException("Student not found", 1);
        }
        Student student = studentOptional.get();
        group.getStudents().remove(student);
        groupRepository.save(group);
    }

    public List<Student> findStudentsByGroup(Integer groupId) {
        logger.debug("finding student by group with ID: {}", groupId);
        Optional<Group> groupOptional = groupRepository.findById(groupId);
        if (!groupOptional.isPresent()) {
            throw new EmptyResultDataAccessException("Group not found", 1);
        }
        List<Student> students = groupOptional.get().getStudents();
        students.sort(Comparator.comparing(Student::getStudentId));
        return students;
    }
}
