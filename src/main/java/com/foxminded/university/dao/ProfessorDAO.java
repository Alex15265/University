package com.foxminded.university.dao;

import com.foxminded.university.dao.entities.Course;
import com.foxminded.university.dao.entities.Professor;
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
public class ProfessorDAO implements DAO<Professor,Integer> {
    private final Logger logger = LoggerFactory.getLogger(ProfessorDAO.class);
    private final SessionFactory sessionFactory;

    @Override
    public Professor create(Professor professor) {
        logger.debug("creating professor: {}", professor);
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.save(professor);
            session.getTransaction().commit();
        }
        return professor;
    }

    @Override
    public List<Professor> readAll() {
        logger.debug("reading all professors");
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from Professor order by professor_id").list();
        }
    }

    @Override
    public Professor readByID(Integer professorId) throws EmptyResultDataAccessException {
        logger.debug("reading professor with ID: {}", professorId);
        try (Session session = sessionFactory.openSession()) {
            return session.get(Professor.class, professorId);
        }
    }

    @Override
    public Professor update(Professor professor) throws NoSuchObjectException {
        logger.debug("updating professor: {}", professor);
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.update(professor);
            session.getTransaction().commit();
        }
        return professor;
    }

    @Override
    public void delete(Integer professorId) {
        logger.debug("deleting professor with ID: {}", professorId);
        Professor professor = new Professor();
        professor.setProfessorId(professorId);
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.delete(professor);
            session.getTransaction().commit();
        }
    }

    public List<Course> findCoursesByProfessor(Integer professorId) {
        logger.debug("finding courses by professor with ID: {}", professorId);
        try (Session session = sessionFactory.openSession()) {
            Professor professor = session.get(Professor.class, professorId);
            if (professor != null) {
                Hibernate.initialize(professor.getCourses());
            }
            return professor.getCourses();
        }
    }
}
