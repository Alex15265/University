package com.foxminded.university.controllers;

import com.foxminded.university.entities.Course;
import com.foxminded.university.entities.Student;
import com.foxminded.university.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class CoursesController {
    private final Logger logger = LoggerFactory.getLogger(CoursesController.class);
    private final CourseService courseService;

    @GetMapping("/courses")
    public String showCourses(Model model) {
        logger.debug("showing all courses");
        model.addAttribute("courses", courseService.readAll());
        return "views/courses/courses";
    }

    @GetMapping("/studentsByCourse/{id}")
    public String showStudentsByCourse(@PathVariable("id") Integer courseId, Model model) {
        logger.debug("showing students by course with ID: {}", courseId);
        model.addAttribute("students", courseService.findStudentsByCourse(courseId));
        return "views/courses/students_by_course";
    }

    @GetMapping("/newCourseForm")
    public String showNewCourseForm(Model model) {
        logger.debug("showing new Course form");
        Course course = new Course();
        model.addAttribute("course", course);
        return "views/courses/new_course";
    }

    @PostMapping("/saveCourse")
    public String saveCourse(@ModelAttribute("course") Course course) {
        logger.debug("saving new course: {}", course);
        courseService.create(course.getCourseName(), course.getDescription(),
                course.getProfessor().getProfessorId());
        return "redirect:/courses";
    }

    @GetMapping("/updateCourseForm/{id}")
    public String showUpdateCourseForm(@PathVariable("id") Integer courseId, Model model) {
        logger.debug("showing update course form");
        model.addAttribute("course", courseService.readByID(courseId));
        return "views/courses/update_course";
    }

    @PostMapping("/updateCourse/{id}")
    public String update(@ModelAttribute("course") Course course, @PathVariable("id") Integer courseId) {
        logger.debug("updating course with ID: {}", courseId);
        courseService.update(courseId, course.getCourseName(), course.getDescription(),
                course.getProfessor().getProfessorId());
        return "redirect:/courses";
    }

    @GetMapping("/deleteCourse/{id}")
    public String deleteCourse(@PathVariable("id") Integer courseId) {
        logger.debug("deleting course with ID: {}", courseId);
        courseService.delete(courseId);
        return "redirect:/courses";
    }

    @GetMapping("/editStudentForm/{id}")
    public String showEditStudentForm(@PathVariable("id") Integer courseId, Model model) {
        logger.debug("showing edit student form");
        model.addAttribute("course", courseService.readByID(courseId));
        Student student = new Student();
        model.addAttribute("student", student);
        return "views/courses/edit_student";
    }

    @PostMapping("/addStudent/{id}")
    public String addStudent(@ModelAttribute("studentId") Integer studentId, @PathVariable("id") Integer courseId,
                           Model model) {
        logger.debug("adding student with ID: {} to course with ID: {}", studentId, courseId);
        courseService.addStudentToCourse(studentId, courseId);
        return "redirect:/courses";
    }

    @PostMapping("/deleteStudent/{id}")
    public String deleteStudent(@ModelAttribute("studentId") Integer studentId, @PathVariable("id") Integer courseId,
                              Model model) {
        logger.debug("deleting student with ID: {} from course with ID: {}", studentId, courseId);
        courseService.deleteStudentFromCourse(studentId, courseId);
        return "redirect:/courses";
    }
}
