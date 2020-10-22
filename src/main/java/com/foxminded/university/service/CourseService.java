package com.foxminded.university.service;

import com.foxminded.university.dao.CourseDAO;
import com.foxminded.university.dao.entities.Course;
import com.foxminded.university.dao.entities.Student;

import java.util.List;

public class CourseService {
    private final CourseDAO courseDAO;

    public CourseService(CourseDAO courseDAO) {
        this.courseDAO = courseDAO;
    }

    public Course create(String courseName, String description) {
        Course course = new Course();
        course.setCourseName(courseName);
        course.setDescription(description);
        return courseDAO.create(course);
    }

    public List<Course> readAll() {
        List<Course> courses = courseDAO.readAll();
        for (Course course: courses) {
            course.setStudents(courseDAO.readStudentsByCourse(course.getCourseId()));
        }
        return courses;
    }

    public Course readByID (Integer courseId) {
        Course course = courseDAO.readByID(courseId);
        course.setStudents(courseDAO.readStudentsByCourse(courseId));
        return course;
    }

    public Course update(Integer courseId, String courseName, String courseDescription) {
        Course course = new Course();
        course.setCourseId(courseId);
        course.setCourseName(courseName);
        course.setDescription(courseDescription);
        return courseDAO.update(course);
    }

    public void delete(Integer courseId) {
        courseDAO.delete(courseId);
    }

    public void addStudentToCourse(Integer studentId, Integer courseId) {
        courseDAO.addStudentToCourse(studentId, courseId);
    }

    public void deleteStudentFromCourse(Integer studentId, Integer courseId) {
        courseDAO.deleteStudentFromCourse(studentId, courseId);
    }

    public List<Student> readStudentsByCourse(Integer courseId) {
        return courseDAO.readStudentsByCourse(courseId);
    }
}
