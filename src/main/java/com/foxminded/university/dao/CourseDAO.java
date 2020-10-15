package com.foxminded.university.dao;

import com.foxminded.university.dao.entities.Course;
import com.foxminded.university.dao.entities.Student;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.util.List;

public class CourseDAO implements DAO<Course,Integer> {
    private static final String UPDATE = "UPDATE courses set course_name = ?, course_description = ? WHERE course_id = ?";
    private static final String READ_BY_ID = "SELECT * FROM courses WHERE course_id = ?";
    private static final String READ_ALL = "SELECT * FROM courses";
    private static final String CREATE = "INSERT INTO courses (course_name, course_description) VALUES (?, ?)";
    private static final String DELETE = "DELETE FROM courses WHERE course_id = ?";
    private static final String READ_BY_COURSE_NAME =
                    "SELECT students.student_id, students.first_name, students.last_name " +
                    "FROM students_courses " +
                    "INNER  JOIN students " +
                    "ON students_courses.student_id = students.student_id " +
                    "WHERE students_courses.course_id = ?";
    private static final String ADD_TO_COURSE = "INSERT INTO students_courses (student_id, course_id) VALUES (?, ?)";
    private static final String DELETE_FROM_COURSE = "DELETE FROM students_courses WHERE student_id = ? AND course_id = ?";
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

    public List<Student> readStudentsByCourse(Integer courseId) {
        return jdbcTemplate.query(READ_BY_COURSE_NAME, (resultSet, rowNum) -> {
            Student student = new Student();
            student.setStudentId(resultSet.getInt("student_id"));
            student.setFirstName(resultSet.getString("first_name"));
            student.setLastName(resultSet.getString("last_name"));
            return student;
        }, courseId);
    }

    public void addStudentToCourse(Integer studentId, Integer courseId) {
        jdbcTemplate.update(ADD_TO_COURSE, studentId, courseId);
    }

    public void deleteStudentFromCourse(Integer studentId, Integer courseId) {
        jdbcTemplate.update(DELETE_FROM_COURSE, studentId, courseId);
    }
}
