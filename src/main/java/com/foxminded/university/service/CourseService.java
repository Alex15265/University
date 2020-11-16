package com.foxminded.university.service;

import com.foxminded.university.dao.CourseDAO;
import com.foxminded.university.dao.entities.Course;
import com.foxminded.university.dao.entities.Student;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;

import java.rmi.NoSuchObjectException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CourseService {
    private final CourseDAO courseDAO;
    private final Logger logger = LoggerFactory.getLogger(CourseService.class);

    public Course create(String courseName, String description, Integer professorId) {
        logger.debug("creating course with courseName: {} and courseDescription: {}", courseName, description);
        Course course = new Course();
        course.setCourseName(courseName);
        course.setDescription(description);
        course.setProfessorId(professorId);
        return courseDAO.create(course);
    }

    public List<Course> readAll() {
        logger.debug("reading all courses");
        return courseDAO.readAll();
    }

    public Course readByID (Integer courseId) throws NoSuchObjectException {
        logger.debug("reading course with ID: {}", courseId);
        try {
            return courseDAO.readByID(courseId);
        } catch (EmptyResultDataAccessException e) {
            logger.warn("reading course with ID: {} exception: {}", courseId, e.getMessage());
            throw new NoSuchObjectException("Object not found");
        }
    }

    public Course update(Integer courseId, String courseName, String courseDescription, Integer professorId) throws NoSuchObjectException {
        logger.debug("updating course with Id: {}, new courseName: {} and description: {}",
                courseId, courseName, courseDescription);
        Course course = new Course();
        course.setCourseId(courseId);
        course.setCourseName(courseName);
        course.setDescription(courseDescription);
        course.setProfessorId(professorId);
        return courseDAO.update(course);
    }

    public void delete(Integer courseId) {
        logger.debug("deleting curse with ID: {}", courseId);
        courseDAO.delete(courseId);
    }

    public void addStudentToCourse(Integer studentId, Integer courseId) {
        logger.debug("adding student with ID: {} to course with ID: {}", studentId, courseId);
        courseDAO.addStudentToCourse(studentId, courseId);
    }

    public void deleteStudentFromCourse(Integer studentId, Integer courseId) {
        logger.debug("deleting student with ID: {} from course with ID: {}", studentId, courseId);
        courseDAO.deleteStudentFromCourse(studentId, courseId);
    }

    public List<Student> findByCourse(Integer courseId) {
        logger.debug("finding students by course with ID: {}", courseId);
        return courseDAO.findByCourse(courseId);
    }
}
