package com.foxminded.university.controllers;

import com.foxminded.university.entities.Student;
import com.foxminded.university.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class StudentsController {
    private final Logger logger = LoggerFactory.getLogger(StudentsController.class);
    private final StudentService studentService;

    @GetMapping("/students")
    public String showStudents(Model model) {
        logger.debug("showing all students");
        model.addAttribute("students", studentService.readAll());
        return "views/students/students";
    }

    @GetMapping("/newStudentForm")
    public String showNewStudentForm(Model model) {
        logger.debug("showing new student form");
        Student student = new Student();
        model.addAttribute("student", student);
        return "views/students/new_student";
    }

    @PostMapping("/saveStudent")
    public String saveStudent(@ModelAttribute("student") @Valid Student student, Errors errors) {
        logger.debug("saving new student: {}", student);
        if (errors.hasErrors()) {
            return "views/students/new_student";
        }
        studentService.create(student.getFirstName(), student.getLastName(), student.getGroup().getGroupId());
        return "redirect:/students";
    }

    @GetMapping("/updateStudentForm/{id}")
    public String showUpdateStudentForm(@PathVariable("id") Integer studentId, Model model) {
        logger.debug("showing update student form");
        model.addAttribute("student", studentService.readByID(studentId));
        return "views/students/update_student";
    }

    @PostMapping("/updateStudent/{id}")
    public String update(@ModelAttribute("student") Student student, @PathVariable("id") Integer studentId) {
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