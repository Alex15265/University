package com.foxminded.university.dao;

import com.foxminded.university.dao.entities.Student;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.util.List;

public class StudentDAO implements DAO<Student,Integer> {
    private static final String UPDATE = "UPDATE students set first_name = ?, last_name = ? WHERE student_id = ?";
    private static final String READ_BY_ID = "SELECT * FROM students WHERE student_id = ?";
    private static final String READ_ALL = "SELECT * FROM students";
    private static final String CREATE = "INSERT INTO students (first_name, last_name) VALUES (?, ?)";
    private static final String DELETE = "DELETE FROM students WHERE student_id = ?";
    private final JdbcTemplate jdbcTemplate;

    public StudentDAO(DriverManagerDataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void create(Student student) {
        jdbcTemplate.update(CREATE, student.getFirstName(), student.getLastName());
    }

    @Override
    public List<Student> readAll() {
        return jdbcTemplate.query(READ_ALL,
                new BeanPropertyRowMapper<>(Student.class));
    }

    @Override
    public Student readByID(Integer id) {
        return jdbcTemplate.queryForObject(READ_BY_ID,
                new Object[] {id}, new BeanPropertyRowMapper<>(Student.class));
    }

    @Override
    public void update(Student student) {
        jdbcTemplate.update(UPDATE,
                student.getFirstName(), student.getLastName(), student.getStudentId());
    }

    @Override
    public void delete(Integer id) {
        jdbcTemplate.update(DELETE, id);
    }
}
