package com.foxminded.university.service;

import com.foxminded.university.dao.StudentDAO;
import com.foxminded.university.dao.entities.Student;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

public class StudentService {
    private final ClassPathXmlApplicationContext context;
    private final StudentDAO studentDAO;

    public StudentService(String configLocation, StudentDAO studentDAO) {
        context = new ClassPathXmlApplicationContext(configLocation);
        this.studentDAO = studentDAO;
    }

    public Student createStudent(String firstName, String lastName) {
        Student student = context.getBean("student", Student.class);
        student.setFirstName(firstName);
        student.setLastName(lastName);
        return studentDAO.create(student);
    }

    public List<Student> readAllStudents() {
        return studentDAO.readAll();
    }

    public Student readStudentByID(Integer studentId) {
        return studentDAO.readByID(studentId);
    }

    public Student updateStudent(Integer studentId, String firstName, String lastName) {
        Student student = context.getBean("student", Student.class);
        student.setStudentId(studentId);
        student.setFirstName(firstName);
        student.setLastName(lastName);
        return studentDAO.update(student);
    }

    public void deleteStudent(Integer studentId) {
        studentDAO.delete(studentId);
    }
}
