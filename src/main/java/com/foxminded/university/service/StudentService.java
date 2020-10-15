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

    public void createStudent(String firstName, String lastName) {
        Student student = context.getBean("student", Student.class);
        student.setFirstName(firstName);
        student.setLastName(lastName);
        studentDAO.create(student);
    }

    public List<Student> readAllStudents() {
        return studentDAO.readAll();
    }

    public Student readStudentByID(Integer id) {
        return studentDAO.readByID(id);
    }

    public void updateStudent(Integer id, String firstName, String lastName) {
        Student student = context.getBean("student", Student.class);
        student.setStudentId(id);
        student.setFirstName(firstName);
        student.setLastName(lastName);
        studentDAO.update(student);
    }

    public void deleteStudent(Integer id) {
        studentDAO.delete(id);
    }
}
