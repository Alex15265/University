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

import java.rmi.NoSuchObjectException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StudentService {
    private final StudentRepository studentRepository;
    private final GroupRepository groupRepository;
    private final Logger logger = LoggerFactory.getLogger(StudentService.class);

    public Student create(String firstName, String lastName, Integer groupId) {
        logger.debug("creating student with firstName: {}, lastName: {} and groupId: {}", firstName, lastName, groupId);
        Student student = new Student();
        student.setFirstName(firstName);
        student.setLastName(lastName);
        Optional<Group> groupOptional = groupRepository.findById(groupId);
        if (!groupOptional.isPresent()) {
            throw new EmptyResultDataAccessException("Student not found", 1);
        }
        student.setGroup(groupOptional.get());
        return studentRepository.save(student);
    }

    public List<Student> readAll() {
        logger.debug("reading all students");
        return studentRepository.findByOrderByStudentIdAsc();
    }

    public Student readByID(Integer studentId) {
        logger.debug("reading student with ID: {}", studentId);
        Optional<Student> studentOptional = studentRepository.findById(studentId);
        if (!studentOptional.isPresent()) {
            throw new EmptyResultDataAccessException("Student not found", 1);
        }
        return studentOptional.get();
    }

    public Student update(Integer studentId, String firstName, String lastName, Integer groupId) {
        logger.debug("updating student with ID: {}, new firstName: {}, lastName: {} and groupId: {}",
                studentId, firstName, lastName, groupId);
        if (!studentRepository.existsById(studentId)) {
            throw new EmptyResultDataAccessException("Student not found", 1);
        }
        Student student = new Student();
        student.setStudentId(studentId);
        student.setFirstName(firstName);
        student.setLastName(lastName);
        Optional<Group> groupOptional = groupRepository.findById(groupId);
        if (!groupOptional.isPresent()) {
            throw new EmptyResultDataAccessException("Group not found", 1);
        }
        student.setGroup(groupOptional.get());
        return studentRepository.save(student);
    }

    public void delete(Integer studentId) {
        logger.debug("deleting student with ID: {}", studentId);
        if (!studentRepository.existsById(studentId)) {
            throw new EmptyResultDataAccessException("Student not found", 1);
        }
        studentRepository.deleteById(studentId);
    }
}
