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
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
        Professor professor = new Professor();
        Optional<Professor> professorOptional = professorRepository.findById(professorId);
        if (professorOptional.isPresent()) {
            professor = professorOptional.get();
        }
        course.setProfessor(professor);
        return courseRepository.save(course);
    }

    public List<Course> readAll() {
        logger.debug("reading all courses");
        List<Course> courses = new ArrayList<>();
        courseRepository.findAll().forEach(courses::add);
        courses.sort(Comparator.comparing(Course::getCourseId));
        return courses;
    }

    public Course readByID (Integer courseId) {
        logger.debug("reading course with ID: {}", courseId);
        Optional<Course> courseOptional = courseRepository.findById(courseId);
        Course course = new Course();
        if (courseOptional.isPresent()) {
            course = courseOptional.get();
        }
        return course;
    }

    public Course update(Integer courseId, String courseName, String courseDescription, Integer professorId) {
        logger.debug("updating course with Id: {}, new courseName: {} and description: {}",
                courseId, courseName, courseDescription);
        Course course = new Course();
        course.setCourseId(courseId);
        course.setCourseName(courseName);
        course.setDescription(courseDescription);
        Professor professor = new Professor();
        Optional<Professor> professorOptional = professorRepository.findById(professorId);
        if (professorOptional.isPresent()) {
            professor = professorOptional.get();
        }
        course.setProfessor(professor);
        return courseRepository.save(course);
    }

    public void delete(Integer courseId) {
        logger.debug("deleting curse with ID: {}", courseId);
        courseRepository.deleteById(courseId);
    }

    public void addStudentToCourse(Integer studentId, Integer courseId) {
        logger.debug("adding student with ID: {} to course with ID: {}", studentId, courseId);
        Optional<Course> courseOptional = courseRepository.findById(courseId);
        Course course = new Course();
        if (courseOptional.isPresent()) {
            course = courseOptional.get();
        }
        Optional<Student> studentOptional = studentRepository.findById(studentId);
        Student student = new Student();
        if (studentOptional.isPresent()) {
            student = studentOptional.get();
        }
        course.getStudents().add(student);
        courseRepository.save(course);
    }

    public void deleteStudentFromCourse(Integer studentId, Integer courseId) {
        logger.debug("deleting student with ID: {} from course with ID: {}", studentId, courseId);
        Optional<Course> courseOptional = courseRepository.findById(courseId);
        Course course = new Course();
        if (courseOptional.isPresent()) {
            course = courseOptional.get();
        }
        Optional<Student> studentOptional = studentRepository.findById(studentId);
        Student student = new Student();
        if (studentOptional.isPresent()) {
            student = studentOptional.get();
        }
        course.getStudents().remove(student);
        courseRepository.save(course);
    }

    public List<Student> findStudentsByCourse(Integer courseId) {
        logger.debug("finding student by course with ID: {}", courseId);
        Optional<Course> courseOptional = courseRepository.findById(courseId);
        List<Student> students = new ArrayList<>();
        if (courseOptional.isPresent()) {
            students = courseOptional.get().getStudents();
        }
        students.sort(Comparator.comparing(Student::getStudentId));
        return students;
    }
}
