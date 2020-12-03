package com.foxminded.university.service;

import com.foxminded.university.entities.Course;
import com.foxminded.university.entities.Professor;
import com.foxminded.university.entities.Student;
import com.foxminded.university.repositories.CourseRepository;
import com.foxminded.university.repositories.ProfessorRepository;
import com.foxminded.university.repositories.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CourseService {
    private final CourseRepository courseRepository;
    private final ProfessorRepository professorRepository;
    private final StudentRepository studentRepository;
    private final Logger logger = LoggerFactory.getLogger(CourseService.class);

    public Course create(String courseName, String description, Integer professorId) {
        logger.debug("creating course with courseName: {} and courseDescription: {}", courseName, description);
        Course course = new Course();
        course.setCourseName(courseName);
        course.setDescription(description);
        Optional<Professor> professorOptional = professorRepository.findById(professorId);
        if (!professorOptional.isPresent()) {
            throw new EmptyResultDataAccessException("Professor not found", 1);
        }
        course.setProfessor(professorOptional.get());
        if (courseRepository.findByCourseName(courseName) != null) {
            throw new IllegalArgumentException("Course already exist");
        }
        return courseRepository.save(course);
    }

    public List<Course> readAll() {
        logger.debug("reading all courses");
        return courseRepository.findByOrderByCourseIdAsc();
    }

    public Course readByID (Integer courseId) {
        logger.debug("reading course with ID: {}", courseId);
        Optional<Course> courseOptional = courseRepository.findById(courseId);
        if (!courseOptional.isPresent()) {
            throw new EmptyResultDataAccessException("Course not found", 1);
        }
        return courseOptional.get();
    }

    public Course update(Integer courseId, String courseName, String courseDescription, Integer professorId) {
        logger.debug("updating course with Id: {}, new courseName: {} and description: {}",
                courseId, courseName, courseDescription);
        if (!courseRepository.existsById(courseId)) {
            throw new EmptyResultDataAccessException("Course not found", 1);
        }
        Course course = new Course();
        course.setCourseId(courseId);
        course.setCourseName(courseName);
        course.setDescription(courseDescription);
        Optional<Professor> professorOptional = professorRepository.findById(professorId);
        if (!professorOptional.isPresent()) {
            throw new EmptyResultDataAccessException("Professor not found", 1);
        }
        course.setProfessor(professorOptional.get());
        return courseRepository.save(course);
    }

    public void delete(Integer courseId) {
        logger.debug("deleting curse with ID: {}", courseId);
        if (!courseRepository.existsById(courseId)) {
            throw new EmptyResultDataAccessException("Course not found", 1);
        }
        courseRepository.deleteById(courseId);
    }

    public void addStudentToCourse(Integer studentId, Integer courseId) {
        logger.debug("adding student with ID: {} to course with ID: {}", studentId, courseId);
        Optional<Course> courseOptional = courseRepository.findById(courseId);
        if (!courseOptional.isPresent()) {
            throw new EmptyResultDataAccessException("Course not found", 1);
        }
        Course course = courseOptional.get();
        Optional<Student> studentOptional = studentRepository.findById(studentId);
        if (!studentOptional.isPresent()) {
            throw new EmptyResultDataAccessException("Student not found", 1);
        }
        Student student = studentOptional.get();
        course.getStudents().add(student);
        courseRepository.save(course);
    }

    public void deleteStudentFromCourse(Integer studentId, Integer courseId) {
        logger.debug("deleting student with ID: {} from course with ID: {}", studentId, courseId);
        Optional<Course> courseOptional = courseRepository.findById(courseId);
        if (!courseOptional.isPresent()) {
            throw new EmptyResultDataAccessException("Course not found", 1);
        }
        Course course = courseOptional.get();
        Optional<Student> studentOptional = studentRepository.findById(studentId);
        if (!studentOptional.isPresent()) {
            throw new EmptyResultDataAccessException("Student not found", 1);
        }
        Student student = studentOptional.get();
        course.getStudents().remove(student);
        courseRepository.save(course);
    }

    public List<Student> findStudentsByCourse(Integer courseId) {
        logger.debug("finding students by course with ID: {}", courseId);
        Optional<Course> courseOptional = courseRepository.findById(courseId);
        if (!courseOptional.isPresent()) {
            throw new EmptyResultDataAccessException("Course not found", 1);
        }
        List<Student> students = courseOptional.get().getStudents();
        students.sort(Comparator.comparing(Student::getStudentId));
        return students;
    }
}
