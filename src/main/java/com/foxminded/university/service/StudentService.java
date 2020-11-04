package com.foxminded.university.service;

import com.foxminded.university.dao.StudentDAO;
import com.foxminded.university.dao.entities.Student;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;

import java.rmi.NoSuchObjectException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class StudentService {
    private final StudentDAO studentDAO;
    private final Logger logger = LoggerFactory.getLogger(StudentService.class);

    public Student create(String firstName, String lastName) {
        logger.debug("creating student with firstName: {} and lastName: {}", firstName, lastName);
        Student student = new Student();
        student.setFirstName(firstName);
        student.setLastName(lastName);
        return studentDAO.create(student);
    }

    public List<Student> readAll() {
        logger.debug("reading all students");
        return studentDAO.readAll();
    }

    public Student readByID(Integer studentId) throws NoSuchObjectException {
        logger.debug("reading student with ID: {}", studentId);
        try {
        return studentDAO.readByID(studentId);
        } catch (EmptyResultDataAccessException e) {
            logger.warn("reading student with ID: {} exception: {}", studentId, e.getMessage());
            throw new NoSuchObjectException("Object not found");
        }
    }

    public Student update(Integer studentId, String firstName, String lastName) throws NoSuchObjectException {
        logger.debug("updating student with ID: {}, new firstName: {} and lastName: {}",
                studentId, firstName, lastName);
        Student student = new Student();
        student.setStudentId(studentId);
        student.setFirstName(firstName);
        student.setLastName(lastName);
        return studentDAO.update(student);
    }

    public void delete(Integer studentId) {
        logger.debug("deleting student with ID: {}", studentId);
        studentDAO.delete(studentId);
    }
}
