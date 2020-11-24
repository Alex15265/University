package com.foxminded.university.dao;

import com.foxminded.university.dao.entities.ClassRoom;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.rmi.NoSuchObjectException;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ClassRoomDAO implements DAO<ClassRoom,Integer> {
    private final Logger logger = LoggerFactory.getLogger(ClassRoomDAO.class);
    private final SessionFactory sessionFactory;

    @Override
    public ClassRoom create(ClassRoom room) {
        logger.debug("creating classroom: {}", room);
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.save(room);
            session.getTransaction().commit();
        }
        return room;
    }

    @Override
    public List<ClassRoom> readAll() {
        logger.debug("reading all rooms");
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from ClassRoom order by room_id").list();
        }
    }

    @Override
    public ClassRoom readByID(Integer classRoomId) throws EmptyResultDataAccessException {
        logger.debug("reading room with ID: {}", classRoomId);
        try (Session session = sessionFactory.openSession()) {
            return session.get(ClassRoom.class, classRoomId);
        }
    }

    @Override
    public ClassRoom update(ClassRoom room) throws NoSuchObjectException {
        logger.debug("updating room: {}", room);
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.update(room);
            session.getTransaction().commit();
        }
        return room;
    }

    @Override
    public void delete(Integer classRoomId) {
        logger.debug("deleting room with ID: {}", classRoomId);
        ClassRoom classRoom = new ClassRoom();
        classRoom.setRoomId(classRoomId);
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.delete(classRoom);
            session.getTransaction().commit();
        }
    }
}
