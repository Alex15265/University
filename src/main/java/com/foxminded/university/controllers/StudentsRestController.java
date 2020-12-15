package com.foxminded.university.controllers;

import com.foxminded.university.entities.Student;
import com.foxminded.university.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class StudentsRestController {
    private final Logger logger = LoggerFactory.getLogger(StudentsRestController.class);
    private final StudentService studentService;

    @GetMapping("/api/students")
    public List<Student> showStudents() {
        logger.debug("showing all students");
        return studentService.readAll();
    }

    @GetMapping("/api/students/{id}")
    public Student showStudent(@PathVariable("id") Integer studentId) {
        logger.debug("showing student with ID: {}", studentId);
        return studentService.readByID(studentId);
    }

    @PostMapping("/api/students")
    public Student saveStudent(@RequestParam String firstName, @RequestParam String lastName,
                               @RequestParam Integer groupId) {
        logger.debug("saving new student with firstName: {}, lastName: {}, groupID: {}", firstName, lastName, groupId);
        return studentService.create(firstName, lastName, groupId);
    }

    @PatchMapping("/api/students/{id}")
    public Student update(@RequestParam String firstName, @RequestParam String lastName,
                         @RequestParam Integer groupId, @PathVariable("id") Integer studentId) {
        logger.debug("updating student with ID: {}", studentId);
        return studentService.update(studentId, firstName, lastName, groupId);
    }

    @DeleteMapping("/api/students/{id}")
    public void deleteStudent(@PathVariable("id") Integer studentId) {
        logger.debug("deleting student with ID: {}", studentId);
        studentService.delete(studentId);
    }
}
