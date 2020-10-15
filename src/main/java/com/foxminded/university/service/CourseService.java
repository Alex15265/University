package com.foxminded.university.service;

import com.foxminded.university.dao.CourseDAO;
import com.foxminded.university.dao.entities.Course;
import com.foxminded.university.dao.entities.Student;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

public class CourseService {
    private final ClassPathXmlApplicationContext context;
    private final CourseDAO courseDAO;
    public CourseService(String configLocation, CourseDAO courseDAO) {
        context = new ClassPathXmlApplicationContext(configLocation);
        this.courseDAO = courseDAO;
    }

    public void createCourse(String courseName, String description) {
        Course course = context.getBean("course", Course.class);
        course.setCourseName(courseName);
        course.setDescription(description);
        courseDAO.create(course);
    }

    public List<Course> readAllCourses() {
        List<Course> courses = courseDAO.readAll();
        for (Course course: courses) {
            course.setStudents(courseDAO.readStudentsByCourse(course.getCourseId()));
        }
        return courses;
    }

    public Course readCourseByID (Integer id) {
        Course course = courseDAO.readByID(id);
        course.setStudents(courseDAO.readStudentsByCourse(course.getCourseId()));
        return course;
    }

    public void updateCourse(Integer courseId, String courseName, String courseDescription) {
        Course course = context.getBean("course", Course.class);
        course.setCourseId(courseId);
        course.setCourseName(courseName);
        course.setDescription(courseDescription);
        courseDAO.update(course);
    }

    public void deleteCourse(Integer courseId) {
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
