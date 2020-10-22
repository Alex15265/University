package com.foxminded.university.service;

import com.foxminded.university.dao.StudentDAO;
import com.foxminded.university.dao.entities.Student;

import java.util.List;

public class StudentService {
    private final StudentDAO studentDAO;

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

    public Student readByID(Integer studentId) {
        return studentDAO.readByID(studentId);
    }

    public Student update(Integer studentId, String firstName, String lastName) {
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
