package com.foxminded.university.dao;

import com.foxminded.university.dao.entities.Student;
import lombok.RequiredArgsConstructor;
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
public class StudentDAO implements DAO<Student,Integer> {
    private final Logger logger = LoggerFactory.getLogger(StudentDAO.class);
    private final SessionFactory sessionFactory;

    @Override
    public Student create(Student student) {
        logger.debug("creating student: {}", student);
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.save(student);
            session.getTransaction().commit();
        }
        return student;
    }

    @Override
    public List<Student> readAll() {
        logger.debug("reading all students");
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from Student order by student_id").list();
        }
    }

    @Override
    public Student readByID(Integer studentId) throws EmptyResultDataAccessException {
        logger.debug("reading student with ID: {}", studentId);
        try (Session session = sessionFactory.openSession()) {
            return session.get(Student.class, studentId);
        }
    }

    @Override
    public Student update(Student student) throws NoSuchObjectException {
        logger.debug("updating student: {}", student);
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.update(student);
            session.getTransaction().commit();
        }
        return student;
    }

    @Override
    public void delete(Integer studentId) {
        logger.debug("deleting student with ID: {}", studentId);
        Student student = new Student();
        student.setStudentId(studentId);
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.delete(student);
            session.getTransaction().commit();
        }
    }
}
