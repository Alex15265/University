package com.foxminded.university.service;

import com.foxminded.university.dao.CourseDAO;
import com.foxminded.university.dao.entities.Course;
import com.foxminded.university.dao.entities.Student;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;

import java.rmi.NoSuchObjectException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CourseService {
    private final CourseDAO courseDAO;

    public Course create(String courseName, String description) {
        Course course = new Course();
        course.setCourseName(courseName);
        course.setDescription(description);
        return courseDAO.create(course);
    }

    public List<Course> readAll() {
        return courseDAO.readAll();
    }

    public Course readByID (Integer courseId) throws NoSuchObjectException {
        try {
            return courseDAO.readByID(courseId);
        } catch (EmptyResultDataAccessException e) {
            throw new NoSuchObjectException("Object not found");
        }
    }

    public Course update(Integer courseId, String courseName, String courseDescription) throws NoSuchObjectException {
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

    public List<Student> findByCourse(Integer courseId) {
        return courseDAO.findByCourse(courseId);
    }
}
