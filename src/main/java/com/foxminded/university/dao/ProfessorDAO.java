package com.foxminded.university.dao;

import com.foxminded.university.dao.entities.Professor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.util.List;

public class ProfessorDAO implements DAO<Professor,Integer> {
    private static final String UPDATE = "UPDATE professors set first_name = ?, last_name = ? WHERE professor_id = ?";
    private static final String READ_BY_ID = "SELECT * FROM professors WHERE professor_id = ?";
    private static final String READ_ALL = "SELECT * FROM professors";
    private static final String CREATE = "INSERT INTO professors (first_name, last_name) VALUES (?, ?)";
    private static final String DELETE = "DELETE FROM professors WHERE professor_id = ?";
    private final JdbcTemplate jdbcTemplate;

    public ProfessorDAO(DriverManagerDataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void create(Professor professor) {
        jdbcTemplate.update(CREATE, professor.getFirstName(), professor.getLastName());
    }

    @Override
    public List<Professor> readAll() {
        return jdbcTemplate.query(READ_ALL, (resultSet, rowNum) -> {
            Professor professor = new Professor();
            professor.setProfessorId(resultSet.getInt("professor_id"));
            professor.setFirstName(resultSet.getString("first_name"));
            professor.setLastName(resultSet.getString("last_name"));
            return professor;
        });
    }

    @Override
    public Professor readByID(Integer id) {
        return jdbcTemplate.queryForObject(READ_BY_ID, (resultSet, rowNum) -> {
            Professor professor = new Professor();
            professor.setProfessorId(resultSet.getInt("professor_id"));
            professor.setFirstName(resultSet.getString("first_name"));
            professor.setLastName(resultSet.getString("last_name"));
            return professor;
        }, id);
    }

    @Override
    public void update(Professor professor) {
        jdbcTemplate.update(UPDATE,
                professor.getFirstName(), professor.getLastName(), professor.getProfessorId());
    }

    @Override
    public void delete(Integer id) {
        jdbcTemplate.update(DELETE, id);
    }
}
