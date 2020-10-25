package com.foxminded.university.service;

import com.foxminded.university.dao.StudentDAO;
import com.foxminded.university.dao.entities.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.util.List;

@Component
public class StudentService {
    private final StudentDAO studentDAO;

    @Autowired
    public StudentService(StudentDAO studentDAO) {
        this.studentDAO = studentDAO;
    }

    public Student create(String firstName, String lastName) {
        Student student = new Student();
        student.setFirstName(firstName);
        student.setLastName(lastName);
        return studentDAO.create(student);
    }

    public List<Student> readAll() {
        return studentDAO.readAll();
    }

    public Student readByID(Integer studentId) throws FileNotFoundException {
        try {
        return studentDAO.readByID(studentId);
        } catch (EmptyResultDataAccessException e) {
            throw new FileNotFoundException();
        }
    }

    public Student update(Integer studentId, String firstName, String lastName) throws FileNotFoundException {
        Student student = new Student();
        student.setStudentId(studentId);
        student.setFirstName(firstName);
        student.setLastName(lastName);
        return studentDAO.update(student);
    }

    public void delete(Integer studentId) {
        studentDAO.delete(studentId);
    }
}
