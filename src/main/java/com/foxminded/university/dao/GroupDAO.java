package com.foxminded.university.dao;

import com.foxminded.university.dao.entities.Group;
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
public class GroupDAO implements DAO<Group,Integer> {
    private final Logger logger = LoggerFactory.getLogger(GroupDAO.class);
    private final SessionFactory sessionFactory;

    @Override
    public Group create(Group group) {
        logger.debug("creating group: {}", group);
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.save(group);
            session.getTransaction().commit();
        }
        return group;
    }

    @Override
    public List<Group> readAll() {
        logger.debug("reading all groups");
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from Group order by group_id").list();
        }
    }

    @Override
    public Group readByID(Integer groupId) throws EmptyResultDataAccessException {
        logger.debug("reading group with ID: {}", groupId);
        try (Session session = sessionFactory.openSession()) {
            return session.get(Group.class, groupId);
        }
    }

    @Override
    public Group update(Group group) throws NoSuchObjectException {
        logger.debug("updating group: {}", group);
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.update(group);
            session.getTransaction().commit();
        }
        return group;
    }

    @Override
    public void delete(Integer groupId) {
        logger.debug("deleting group with ID: {}", groupId);
        Group group = new Group();
        group.setGroupId(groupId);
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.delete(group);
            session.getTransaction().commit();
        }
    }

    public void addStudentToGroup(Integer studentId, Integer groupId) {
        logger.debug("adding student with ID: {} to group with ID: {}", studentId, groupId);
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Student student = session.load(Student.class, studentId);
            Group group = session.load(Group.class, groupId);
            group.getStudents().add(student);
            session.update(group);
            session.getTransaction().commit();
        }
    }

    public void deleteStudentFromGroup(Integer studentId, Integer groupId) {
        logger.debug("deleting student with ID: {} from his group", studentId);
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Student student = session.load(Student.class, studentId);
            Group group = session.load(Group.class, groupId);
            group.getStudents().remove(student);
            session.update(group);
            session.getTransaction().commit();
        }
    }

    public List<Student> findStudentsByGroup(Integer groupId) {
        logger.debug("finding students by group with ID: {}", groupId);
        try (Session session = sessionFactory.openSession()) {
            Group group = session.get(Group.class, groupId);
            if (group != null) {
                Hibernate.initialize(group.getStudents());
            }
            return group.getStudents();
        }
    }
}
