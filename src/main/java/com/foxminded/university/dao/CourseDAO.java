package com.foxminded.university.dao;

import com.foxminded.university.config.DriverManagerDataSourceInitializer;
import com.foxminded.university.dao.entities.Course;
import com.foxminded.university.dao.entities.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.rmi.NoSuchObjectException;
import java.sql.PreparedStatement;
import java.util.List;

@Component
public class CourseDAO implements DAO<Course,Integer> {
    private static final String UPDATE = "UPDATE courses set course_name = ?, course_description = ? WHERE course_id = ?";
    private static final String READ_BY_ID = "SELECT * FROM courses WHERE course_id = ?";
    private static final String READ_ALL = "SELECT * FROM courses";
    private static final String CREATE = "INSERT INTO courses (course_name, course_description) VALUES (?, ?)";
    private static final String DELETE = "DELETE FROM courses WHERE course_id = ?";
    private static final String READ_STUDENT_BY_COURSE =
                    "SELECT students.student_id, students.first_name, students.last_name " +
                    "FROM students_courses " +
                    "INNER  JOIN students " +
                    "ON students_courses.student_id = students.student_id " +
                    "WHERE students_courses.course_id = ?";
    private static final String ADD_STUDENT_TO_COURSE = "INSERT INTO students_courses (student_id, course_id) VALUES (?, ?)";
    private static final String DELETE_STUDENT_FROM_COURSE = "DELETE FROM students_courses WHERE student_id = ? AND course_id = ?";
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public CourseDAO(DriverManagerDataSourceInitializer initializer) {
        jdbcTemplate = initializer.initialize();
    }

    @Override
    public Course create(Course course) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
                    PreparedStatement resultSet =
                            connection.prepareStatement(CREATE, new String[] {"course_id"});
                    resultSet.setString(1, course.getCourseName());
                    resultSet.setString(2, course.getDescription());
                    return resultSet;
                },
                keyHolder);
        course.setCourseId((Integer) keyHolder.getKey());
        return course;
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
    public Course readByID(Integer id) throws EmptyResultDataAccessException {
        return jdbcTemplate.queryForObject(READ_BY_ID, (resultSet, rowNum) -> {
            Course course = new Course();
            course.setCourseId(resultSet.getInt("course_id"));
            course.setCourseName(resultSet.getString("course_name"));
            course.setDescription(resultSet.getString("course_description"));
            return course;
            }, id);
    }

    @Override
    public Course update(Course course) throws NoSuchObjectException {
        int count = jdbcTemplate.update(connection -> {
            PreparedStatement resultSet =
                    connection.prepareStatement(UPDATE, new String[] {"course_id"});
            resultSet.setString(1, course.getCourseName());
            resultSet.setString(2, course.getDescription());
            resultSet.setInt(3, course.getCourseId());
            return resultSet;
        });
        if (count == 0) throw new NoSuchObjectException("Object not found");
        return course;
    }

    @Override
    public void delete(Integer id) {
        jdbcTemplate.update(DELETE, id);
    }

    public List<Student> findByCourse(Integer courseId) {
        return jdbcTemplate.query(READ_STUDENT_BY_COURSE, (resultSet, rowNum) -> {
            Student student = new Student();
            student.setStudentId(resultSet.getInt("student_id"));
            student.setFirstName(resultSet.getString("first_name"));
            student.setLastName(resultSet.getString("last_name"));
            return student;
        }, courseId);
    }

    public void addStudentToCourse(Integer studentId, Integer courseId) {
        jdbcTemplate.update(ADD_STUDENT_TO_COURSE, studentId, courseId);
    }

    public void deleteStudentFromCourse(Integer studentId, Integer courseId) {
        jdbcTemplate.update(DELETE_STUDENT_FROM_COURSE, studentId, courseId);
    }
}
