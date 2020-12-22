package com.foxminded.university.controllers.rest;

import com.foxminded.university.dto.courses.CourseDTORequest;
import com.foxminded.university.dto.courses.CourseDTOResponse;
import com.foxminded.university.dto.student.StudentDTOResponse;
import com.foxminded.university.entities.Course;
import com.foxminded.university.entities.Student;
import com.foxminded.university.mappers.CourseMapper;
import com.foxminded.university.mappers.StudentMapper;
import com.foxminded.university.service.CourseService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/courses")
public class CoursesRestController {
    private final Logger logger = LoggerFactory.getLogger(CoursesRestController.class);
    private final CourseService courseService;

    @GetMapping("/")
    @ApiOperation(value = "Method used to fetch all courses")
    public List<CourseDTOResponse> showAllCourses() {
        logger.debug("showing all courses");
        List<Course> courses = courseService.readAll();
        return courses.stream().map(CourseMapper.INSTANCE::courseToCourseDTOResponse).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Method used to fetch a course by ID")
    public CourseDTOResponse showCourseByID(
            @ApiParam(value = "ID value of the course you need to retrieve", required = true)
            @PathVariable("id") Integer courseId) {
        logger.debug("showing course with ID: {}", courseId);
        Course course = courseService.readByID(courseId);
        return CourseMapper.INSTANCE.courseToCourseDTOResponse(course);
    }

    @GetMapping("/{id}/students")
    @ApiOperation(value = "Method used to show students in a course")
    public List<StudentDTOResponse> showStudentsByCourse(
            @ApiParam(value = "ID value of the course whose students you want to retrieve", required = true)
            @PathVariable("id") Integer courseId) {
        logger.debug("showing students by course with ID: {}", courseId);
        List<Student> students = courseService.findStudentsByCourse(courseId);
        return students.stream().map(StudentMapper.INSTANCE::studentToStudentDTOResponse).collect(Collectors.toList());
    }

    @PostMapping("")
    @ApiOperation(value = "Method used to save a new course")
    public CourseDTOResponse saveCourse(
            @ApiParam(value = "courseDTORequest for the course you need to save", required = true)
            @Valid @RequestBody CourseDTORequest courseDTORequest) {
        logger.debug("saving new course: {}}", courseDTORequest);
        Course course = courseService.create(courseDTORequest.getCourseName(), courseDTORequest.getDescription(),
                courseDTORequest.getProfessorId());
        return CourseMapper.INSTANCE.courseToCourseDTOResponse(course);
    }

    @PatchMapping("/{id}")
    @ApiOperation(value = "Method used to update a course by ID")
    public CourseDTOResponse updateCourse(
            @ApiParam(value = "courseDTORequest for the course you need to update", required = true)
            @Valid @RequestBody CourseDTORequest courseDTORequest,
            @ApiParam(value = "ID value of the course you need to update", required = true)
            @PathVariable("id") Integer courseId) {
        logger.debug("updating course with ID: {}", courseId);
        Course course = courseService.update(courseId, courseDTORequest.getCourseName(),
                courseDTORequest.getDescription(), courseDTORequest.getProfessorId());
        return CourseMapper.INSTANCE.courseToCourseDTOResponse(course);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Method used to delete a course by ID")
    public void deleteCourse(
            @ApiParam(value = "ID value of the course you need to delete", required = true)
            @PathVariable("id") Integer courseId) {
        logger.debug("deleting course with ID: {}", courseId);
        courseService.delete(courseId);
    }

    @PostMapping("/{id}/students")
    @ApiOperation(value = "Method used to add a student to a course")
    public void addStudentToCourse(
            @ApiParam(value = "ID value of the student you need to add", required = true)
            @RequestParam Integer studentId,
            @ApiParam(value = "ID value of the course whose students you need to update", required = true)
            @PathVariable("id") Integer courseId) {
        logger.debug("adding student with ID: {} to course with ID: {}", studentId, courseId);
        courseService.addStudentToCourse(studentId, courseId);
    }

    @DeleteMapping("/{id}/students")
    @ApiOperation(value = "Method used to delete a student from a course")
    public void deleteStudentFromCourse(
            @ApiParam(value = "ID value of the student you need to delete", required = true)
            @RequestParam Integer studentId,
            @ApiParam(value = "ID value of the course whose students you need to update", required = true)
            @PathVariable("id") Integer courseId) {
        logger.debug("adding student with ID: {} to course with ID: {}", studentId, courseId);
        courseService.deleteStudentFromCourse(studentId, courseId);
    }
}