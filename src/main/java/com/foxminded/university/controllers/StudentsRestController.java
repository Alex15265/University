package com.foxminded.university.controllers;

import com.foxminded.university.dto.student.StudentDTORequest;
import com.foxminded.university.dto.student.StudentDTOResponse;
import com.foxminded.university.entities.Student;
import com.foxminded.university.mappers.StudentMapper;
import com.foxminded.university.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class StudentsRestController {
    private final Logger logger = LoggerFactory.getLogger(StudentsRestController.class);
    private final StudentService studentService;

    @GetMapping("/api/students")
    public List<StudentDTOResponse> showStudents() {
        logger.debug("showing all students");
        List<Student> students = studentService.readAll();
        return students.stream().map(StudentMapper.INSTANCE::studentToStudentDTOResponse).collect(Collectors.toList());
    }

    @GetMapping("/api/students/{id}")
    public StudentDTOResponse showStudent(@PathVariable("id") Integer studentId) {
        logger.debug("showing student with ID: {}", studentId);
        Student student = studentService.readByID(studentId);
        return StudentMapper.INSTANCE.studentToStudentDTOResponse(student);
    }

    @PostMapping("/api/students")
    public StudentDTOResponse saveStudent(@Valid @RequestBody StudentDTORequest studentDTORequest) {
        logger.debug("saving new student: {}", studentDTORequest);
        Student student = studentService.create(studentDTORequest.getFirstName(), studentDTORequest.getLastName(),
                studentDTORequest.getGroupId());
        return StudentMapper.INSTANCE.studentToStudentDTOResponse(student);
    }

    @PatchMapping("/api/students/{id}")
    public StudentDTOResponse update(@Valid @RequestBody StudentDTORequest studentDTORequest,
                                     @PathVariable("id") Integer studentId) {
        logger.debug("updating student: {}", studentDTORequest);
        Student student = studentService.update(studentId, studentDTORequest.getFirstName(),
                studentDTORequest.getLastName(), studentDTORequest.getGroupId());
        return StudentMapper.INSTANCE.studentToStudentDTOResponse(student);
    }

    @DeleteMapping("/api/students/{id}")
    public void deleteStudent(@PathVariable("id") Integer studentId) {
        logger.debug("deleting student with ID: {}", studentId);
        studentService.delete(studentId);
    }
}
