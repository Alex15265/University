package com.foxminded.university.dao;

import com.foxminded.university.dao.entities.LessonTime;
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
public class LessonTimeDAO implements DAO<LessonTime,Integer> {
    private final Logger logger = LoggerFactory.getLogger(LessonTimeDAO.class);
    private final SessionFactory sessionFactory;

    @Override
    public LessonTime create(LessonTime lessonTime) {
        logger.debug("creating lessonTime: {}", lessonTime);
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.save(lessonTime);
            session.getTransaction().commit();
        }
        return lessonTime;
    }

    @Override
    public List<LessonTime> readAll() {
        logger.debug("reading all lessonTimes");
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from LessonTime order by time_id").list();
        }
    }

    @Override
    public LessonTime readByID(Integer lessonTimeId) throws EmptyResultDataAccessException {
        logger.debug("reading lessonTime with ID: {}", lessonTimeId);
        try (Session session = sessionFactory.openSession()) {
            return session.get(LessonTime.class, lessonTimeId);
        }
    }

    @Override
    public LessonTime update(LessonTime lessonTime) throws NoSuchObjectException {
        logger.debug("updating lessonTime: {}", lessonTime);
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.update(lessonTime);
            session.getTransaction().commit();
        }
        return lessonTime;
    }

    @Override
    public void delete(Integer lessonTimeId) {
        logger.debug("deleting lessonTime with ID: {}", lessonTimeId);
        LessonTime lessonTime = new LessonTime();
        lessonTime.setTimeId(lessonTimeId);
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.delete(lessonTime);
            session.getTransaction().commit();
        }
    }
}
