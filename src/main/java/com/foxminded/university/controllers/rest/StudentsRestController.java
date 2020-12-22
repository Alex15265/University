package com.foxminded.university.controllers.rest;

import com.foxminded.university.dto.student.StudentDTORequest;
import com.foxminded.university.dto.student.StudentDTOResponse;
import com.foxminded.university.entities.Student;
import com.foxminded.university.mappers.StudentMapper;
import com.foxminded.university.service.StudentService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/students")
public class StudentsRestController {
    private final Logger logger = LoggerFactory.getLogger(StudentsRestController.class);
    private final StudentService studentService;

    @GetMapping("/")
    @ApiOperation(value = "Method used to fetch all students")
    public List<StudentDTOResponse> showAllStudents() {
        logger.debug("showing all students");
        List<Student> students = studentService.readAll();
        return students.stream().map(StudentMapper.INSTANCE::studentToStudentDTOResponse).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Method used to fetch a student by ID")
    public StudentDTOResponse showStudentByID(
            @ApiParam(value = "ID value of the student you need to retrieve", required = true)
            @PathVariable("id") Integer studentId) {
        logger.debug("showing student with ID: {}", studentId);
        Student student = studentService.readByID(studentId);
        return StudentMapper.INSTANCE.studentToStudentDTOResponse(student);
    }

    @PostMapping("/")
    @ApiOperation(value = "Method used to save a new student")
    public StudentDTOResponse saveStudent(
            @ApiParam(value = "studentDTORequest for the student you need to save", required = true)
            @Valid @RequestBody StudentDTORequest studentDTORequest) {
        logger.debug("saving new student: {}", studentDTORequest);
        Student student = studentService.create(studentDTORequest.getFirstName(), studentDTORequest.getLastName(),
                studentDTORequest.getGroupId());
        return StudentMapper.INSTANCE.studentToStudentDTOResponse(student);
    }

    @PatchMapping("/{id}")
    @ApiOperation(value = "Method used to update a student by ID")
    public StudentDTOResponse updateStudent(
            @ApiParam(value = "studentDTORequest for the student you need to update", required = true)
            @Valid @RequestBody StudentDTORequest studentDTORequest,
            @ApiParam(value = "ID value of the student you need to update", required = true)
            @PathVariable("id") Integer studentId) {
        logger.debug("updating student: {}", studentDTORequest);
        Student student = studentService.update(studentId, studentDTORequest.getFirstName(),
                studentDTORequest.getLastName(), studentDTORequest.getGroupId());
        return StudentMapper.INSTANCE.studentToStudentDTOResponse(student);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Method used to delete a student by ID")
    public void deleteStudent(
            @ApiParam(value = "ID value of the student you need to delete", required = true)
            @PathVariable("id") Integer studentId) {
        logger.debug("deleting student with ID: {}", studentId);
        studentService.delete(studentId);
    }
}
