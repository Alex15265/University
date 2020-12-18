package com.foxminded.university.controllers;

import com.foxminded.university.dto.courses.CourseDTORequest;
import com.foxminded.university.dto.courses.CourseDTOResponse;
import com.foxminded.university.dto.student.StudentDTOResponse;
import com.foxminded.university.entities.Course;
import com.foxminded.university.entities.Student;
import com.foxminded.university.mappers.CourseMapper;
import com.foxminded.university.mappers.StudentMapper;
import com.foxminded.university.service.CourseService;
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
public class CoursesRestController {
    private final Logger logger = LoggerFactory.getLogger(CoursesRestController.class);
    private final CourseService courseService;

    @GetMapping("/api/courses")
    public List<CourseDTOResponse> showCourses(Model model) {
        logger.debug("showing all courses");
        List<Course> courses = courseService.readAll();
        return courses.stream().map(CourseMapper.INSTANCE::courseToCourseDTOResponse).collect(Collectors.toList());
    }

    @GetMapping("/api/courses/{id}")
    public CourseDTOResponse showCourse(@PathVariable("id") Integer courseId) {
        logger.debug("showing course with ID: {}", courseId);
        Course course = courseService.readByID(courseId);
        return CourseMapper.INSTANCE.courseToCourseDTOResponse(course);
    }

    @GetMapping("/api/courses/{id}/students")
    public List<StudentDTOResponse> showStudentsByCourse(@PathVariable("id") Integer courseId) {
        logger.debug("showing students by course with ID: {}", courseId);
        List<Student> students = courseService.findStudentsByCourse(courseId);
        return students.stream().map(StudentMapper.INSTANCE::studentToStudentDTOResponse).collect(Collectors.toList());
    }

    @PostMapping("/api/courses")
    public CourseDTOResponse saveCourse(@Valid @RequestBody CourseDTORequest courseDTORequest) {
        logger.debug("saving new course: {}}", courseDTORequest);
        Course course = courseService.create(courseDTORequest.getCourseName(), courseDTORequest.getDescription(),
                courseDTORequest.getProfessorId());
        return CourseMapper.INSTANCE.courseToCourseDTOResponse(course);
    }

    @PatchMapping("/api/courses/{id}")
    public CourseDTOResponse update(@Valid @RequestBody CourseDTORequest courseDTORequest,
                                    @PathVariable("id") Integer courseId) {
        logger.debug("updating course with ID: {}", courseId);
        Course course = courseService.update(courseId, courseDTORequest.getCourseName(),
                courseDTORequest.getDescription(), courseDTORequest.getProfessorId());
        return CourseMapper.INSTANCE.courseToCourseDTOResponse(course);
    }

    @DeleteMapping("/api/courses/{id}")
    public void deleteCourse(@PathVariable("id") Integer courseId) {
        logger.debug("deleting course with ID: {}", courseId);
        courseService.delete(courseId);
    }

    @PostMapping("/api/courses/{id}/students")
    public void addStudent(@RequestParam Integer studentId, @PathVariable("id") Integer courseId) {
        logger.debug("adding student with ID: {} to course with ID: {}", studentId, courseId);
        courseService.addStudentToCourse(studentId, courseId);
    }

    @DeleteMapping("/api/courses/{id}/students")
    public void deleteStudent(@RequestParam Integer studentId, @PathVariable("id") Integer courseId) {
        logger.debug("adding student with ID: {} to course with ID: {}", studentId, courseId);
        courseService.deleteStudentFromCourse(studentId, courseId);
    }
}