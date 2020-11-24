package com.foxminded.university.controllers;

import com.foxminded.university.dao.entities.Student;
import com.foxminded.university.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.rmi.NoSuchObjectException;

@Controller
@RequiredArgsConstructor
public class StudentsController {
    private final Logger logger = LoggerFactory.getLogger(StudentsController.class);
    private final StudentService studentService;

    @GetMapping("/students")
    public String showStudents(Model model) {
        logger.debug("showing all students");
        model.addAttribute("students", studentService.readAll());
        return "students/students";
    }

    @GetMapping("/newStudentForm")
    public String showNewStudentForm(Model model) {
        logger.debug("showing new student form");
        Student student = new Student();
        model.addAttribute("student", student);
        return "students/new_student";
    }

    @PostMapping("/saveStudent")
    public String saveStudent(@ModelAttribute("student") Student student) {
        logger.debug("saving new student: {}", student);
        studentService.create(student.getFirstName(), student.getLastName(), student.getGroup().getGroupId());
        return "redirect:/students";
    }

    @GetMapping("/updateStudentForm/{id}")
    public String showUpdateStudentForm(@PathVariable("id") Integer studentId, Model model) throws NoSuchObjectException {
        logger.debug("showing update student form");
        model.addAttribute("student", studentService.readByID(studentId));
        return "students/update_student";
    }

    @PostMapping("/updateStudent/{id}")
    public String update(@ModelAttribute("student") Student student, @PathVariable("id") Integer studentId) throws NoSuchObjectException {
        logger.debug("updating student with ID: {}", studentId);
        studentService.update(studentId, student.getFirstName(), student.getLastName(), student.getGroup().getGroupId());
        return "redirect:/students";
    }

    @GetMapping("/deleteStudent/{id}")
    public String deleteStudent(@PathVariable("id") Integer studentId) {
        logger.debug("deleting student with ID: {}", studentId);
        studentService.delete(studentId);
        return "redirect:/students";
    }
}