package com.foxminded.university.dao;

import com.foxminded.university.dao.entities.Course;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.util.List;

public class CourseDAO implements DAO<Course,Integer> {
    private static final String UPDATE = "UPDATE courses set course_name = ?, course_description = ? WHERE course_id = ?";
    private static final String READ_BY_ID = "SELECT * FROM courses WHERE course_id = ?";
    private static final String READ_ALL = "SELECT * FROM courses";
    private static final String CREATE = "INSERT INTO courses (course_name, course_description) VALUES (?, ?)";
    private static final String DELETE = "DELETE FROM courses WHERE course_id = ?";
    private final JdbcTemplate jdbcTemplate;

    public CourseDAO(DriverManagerDataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void create(Course course) {
        jdbcTemplate.update(CREATE, course.getCourseName(), course.getDescription());
    }

    @Override
    public List<Course> readAll() {
        return jdbcTemplate.query(READ_ALL, (resultSet, rowNum) -> {
            Course course = new Course();
            course.setCourseId(resultSet.getInt("course_id"));
            course.setCourseName(resultSet.getString("course_name"));
            course.setDescription(resultSet.getString("course_description"));
            return course;
        });
    }

    @Override
    public Course readByID(Integer id) {
        return jdbcTemplate.queryForObject(READ_BY_ID, (resultSet, rowNum) -> {
            Course course = new Course();
            course.setCourseId(resultSet.getInt("course_id"));
            course.setCourseName(resultSet.getString("course_name"));
            course.setDescription(resultSet.getString("course_description"));
            return course;
            }, id);
    }

    @Override
    public void update(Course course) {
        jdbcTemplate.update(UPDATE,
                course.getCourseName(), course.getDescription(), course.getCourseId());
    }

    @Override
    public void delete(Integer id) {
        jdbcTemplate.update(DELETE, id);
    }
}
