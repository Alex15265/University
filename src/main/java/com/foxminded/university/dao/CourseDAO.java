package com.foxminded.university.dao;

import com.foxminded.university.dao.entities.Course;
import com.foxminded.university.dao.entities.Student;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;

import java.rmi.NoSuchObjectException;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CourseDAO implements DAO<Course,Integer> {
    private final Logger logger = LoggerFactory.getLogger(CourseDAO.class);
    private final SessionFactory sessionFactory;

    @Override
    public Course create(Course course) {
        logger.debug("creating course: {}", course);
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.save(course);
            session.getTransaction().commit();
        }
        return course;
    }

    @Override
    public List<Course> readAll() {
        logger.debug("reading all courses");
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from Course order by course_id").list();
        }
    }

    @Override
    public Course readByID(Integer courseId) throws EmptyResultDataAccessException {
        logger.debug("reading course with ID: {}", courseId);
        try (Session session = sessionFactory.openSession()) {
            return session.get(Course.class, courseId);
        }
    }

    @Override
    public Course update(Course course) throws NoSuchObjectException {
        logger.debug("updating course: {}", course);
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.update(course);
            session.getTransaction().commit();
        }
        return course;
    }

    @Override
    public void delete(Integer courseId) {
        logger.debug("deleting course with ID: {}", courseId);
        Course course = new Course();
        course.setCourseId(courseId);
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.delete(course);
            session.getTransaction().commit();
        }
    }

    public void addStudentToCourse(Integer studentId, Integer courseId) {
        logger.debug("adding student with ID: {} to course with ID: {}", studentId, courseId);
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Student student = session.load(Student.class, studentId);
            Course course = session.load(Course.class, courseId);
            course.getStudents().add(student);
            session.update(course);
            session.getTransaction().commit();
        }
    }

    public void deleteStudentFromCourse(Integer studentId, Integer courseId) {
        logger.debug("deleting student with ID: {} from course with ID: {}", studentId, courseId);
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Student student = session.load(Student.class, studentId);
            Course course = session.load(Course.class, courseId);
            course.getStudents().remove(student);
            session.update(course);
            session.getTransaction().commit();
        }
    }

    public List<Student> findStudentsByCourse(Integer courseId) {
        logger.debug("finding student by course with ID: {}", courseId);
        try (Session session = sessionFactory.openSession()) {
            Course course = session.get(Course.class, courseId);
            if (course != null) {
                Hibernate.initialize(course.getStudents());
            }
            return course.getStudents();
        }
    }
}
