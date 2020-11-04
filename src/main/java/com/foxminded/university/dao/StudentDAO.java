package com.foxminded.university.dao;

import com.foxminded.university.dao.entities.Student;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.rmi.NoSuchObjectException;
import java.sql.PreparedStatement;
import java.util.List;

@Component
@RequiredArgsConstructor
public class StudentDAO implements DAO<Student,Integer> {
    private static final String CREATE = "INSERT INTO students (first_name, last_name) VALUES (?, ?)";
    private static final String READ_ALL = "SELECT * FROM students";
    private static final String READ_BY_ID = "SELECT * FROM students WHERE student_id = ?";
    private static final String UPDATE = "UPDATE students set first_name = ?, last_name = ? WHERE student_id = ?";
    private static final String DELETE = "DELETE FROM students WHERE student_id = ?";
    private final Logger logger = LoggerFactory.getLogger(StudentDAO.class);
    private final JdbcTemplate jdbcTemplate;

    @Override
    public Student create(Student student) {
        logger.debug("creating student: {}", student);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
                    PreparedStatement resultSet =
                    connection.prepareStatement(CREATE, new String[] {"student_id"});
                    resultSet.setString(1, student.getFirstName());
                    resultSet.setString(2, student.getLastName());
                    return resultSet;
                },
                keyHolder);
        student.setStudentId((Integer) keyHolder.getKey());
        return student;
    }

    @Override
    public List<Student> readAll() {
        logger.debug("reading all students");
        return jdbcTemplate.query(READ_ALL,
                new BeanPropertyRowMapper<>(Student.class));
    }

    @Override
    public Student readByID(Integer studentId) throws EmptyResultDataAccessException {
        logger.debug("reading student with ID: {}", studentId);
        return jdbcTemplate.queryForObject(READ_BY_ID,
                new Object[] {studentId}, new BeanPropertyRowMapper<>(Student.class));
    }

    @Override
    public Student update(Student student) throws NoSuchObjectException {
        logger.debug("updating student: {}", student);
        int count = jdbcTemplate.update(connection -> {
            PreparedStatement resultSet =
                    connection.prepareStatement(UPDATE, new String[] {"student_id"});
            resultSet.setString(1, student.getFirstName());
            resultSet.setString(2, student.getLastName());
            resultSet.setInt(3, student.getStudentId());
            return resultSet;
        });
        if (count == 0) {
            logger.warn("updating student: {} exception: {}", student, "Object not found");
            throw new NoSuchObjectException("Object not found");
        }
        return student;
    }

    @Override
    public void delete(Integer studentId) {
        logger.debug("deleting student with ID: {}", studentId);
        jdbcTemplate.update(DELETE, studentId);
    }
}
