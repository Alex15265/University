package com.foxminded.university.dao;

import com.foxminded.university.dao.entities.Group;
import com.foxminded.university.dao.entities.Lesson;
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
public class LessonDAO implements DAO<Lesson,Integer> {
    private final Logger logger = LoggerFactory.getLogger(LessonDAO.class);
    private final SessionFactory sessionFactory;

    @Override
    public Lesson create(Lesson lesson) {
        logger.debug("creating lesson: {}", lesson);
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.save(lesson);
            session.getTransaction().commit();
        }
        return lesson;
    }

    @Override
    public List<Lesson> readAll() {
        logger.debug("reading all lessons");
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from Lesson order by lesson_id").list();
        }
    }

    @Override
    public Lesson readByID(Integer lessonId) throws EmptyResultDataAccessException {
        logger.debug("reading lesson with ID: {}", lessonId);
        try (Session session = sessionFactory.openSession()) {
            return session.get(Lesson.class, lessonId);
        }
    }

    @Override
    public Lesson update(Lesson lesson) throws NoSuchObjectException {
        logger.debug("updating lesson: {}", lesson);
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.update(lesson);
            session.getTransaction().commit();
        }
        return lesson;
    }

    @Override
    public void delete(Integer lessonId) {
        logger.debug("deleting lesson with ID: {}", lessonId);
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Lesson lesson = session.load(Lesson.class, lessonId);
            if (lesson != null) {
                session.delete(lesson);
                session.getTransaction().commit();
            }
        }
    }

    public void addGroupToLesson(Integer groupId, Integer lessonId) {
        logger.debug("adding group with ID: {} to lesson with ID: {}", groupId, lessonId);
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Group group = session.load(Group.class, groupId);
            Lesson lesson = session.load(Lesson.class, lessonId);
            lesson.getGroups().add(group);
            session.update(lesson);
            session.getTransaction().commit();
        }
    }

    public void deleteGroupFromLesson(Integer groupId, Integer lessonId) {
        logger.debug("deleting group with ID: {} from lesson with ID: {}", groupId, lessonId);
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Group group = session.load(Group.class, groupId);
            Lesson lesson = session.load(Lesson.class, lessonId);
            lesson.getGroups().remove(group);
            session.update(lesson);
            session.getTransaction().commit();
        }
    }
}
