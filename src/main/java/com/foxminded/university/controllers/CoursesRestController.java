package com.foxminded.university.controllers;

import com.foxminded.university.entities.Course;
import com.foxminded.university.entities.Student;
import com.foxminded.university.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CoursesRestController {
    private final Logger logger = LoggerFactory.getLogger(CoursesRestController.class);
    private final CourseService courseService;

    @GetMapping("/api/courses")
    public List<Course> showCourses(Model model) {
        logger.debug("showing all courses");
        return courseService.readAll();
    }

    @GetMapping("/api/courses/{id}")
    public Course showCourse(@PathVariable("id") Integer courseId) {
        logger.debug("showing course with ID: {}", courseId);
        return courseService.readByID(courseId);
    }

    @GetMapping("/api/studentsByCourse/{id}")
    public List<Student> showStudentsByCourse(@PathVariable("id") Integer courseId) {
        logger.debug("showing students by course with ID: {}", courseId);
        return courseService.findStudentsByCourse(courseId);
    }

    @GetMapping("/api/saveCourse")
    public Course saveCourse(@RequestParam String courseName, @RequestParam String courseDescription,
                             @RequestParam Integer professorId) {
        logger.debug("saving new course with courseName: {}, courseDescription: {}, professorId: {}", courseName,
                courseDescription, professorId);
        return courseService.create(courseName, courseDescription, professorId);
    }

    @GetMapping("/api/updateCourse/{id}")
    public Course update(@RequestParam String courseName, @RequestParam String courseDescription,
                         @RequestParam Integer professorId, @PathVariable("id") Integer courseId) {
        logger.debug("updating course with ID: {}", courseId);
        return courseService.update(courseId, courseName, courseDescription, professorId);
    }

    @GetMapping("/api/deleteCourse/{id}")
    public void deleteCourse(@PathVariable("id") Integer courseId) {
        logger.debug("deleting course with ID: {}", courseId);
        courseService.delete(courseId);
    }

    @GetMapping("/api/addStudentToCourse/{id}")
    public void addStudent(@RequestParam Integer studentId, @PathVariable("id") Integer courseId) {
        logger.debug("adding student with ID: {} to course with ID: {}", studentId, courseId);
        courseService.addStudentToCourse(studentId, courseId);
    }

    @GetMapping("/api/deleteStudentFromCourse/{id}")
    public void deleteStudent(@RequestParam Integer studentId, @PathVariable("id") Integer courseId) {
        logger.debug("adding student with ID: {} to course with ID: {}", studentId, courseId);
        courseService.deleteStudentFromCourse(studentId, courseId);
    }
}