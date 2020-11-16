package com.foxminded.university.dao;

import com.foxminded.university.dao.entities.Course;
import com.foxminded.university.dao.entities.Student;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.rmi.NoSuchObjectException;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CourseDAO implements DAO<Course,Integer> {
    private static final String CREATE = "INSERT INTO courses (course_name, course_description, professor_id) " +
            "VALUES (?, ?, ?)";
    private static final String READ_ALL =
            "SELECT courses.course_id, courses.course_name, courses.course_description, courses.professor_id, " +
            "string_agg(students.student_id::text, ','), " +
            "string_agg(students.first_name, ','), " +
            "string_agg(students.last_name, ',') " +
            "FROM students_courses " +
            "INNER JOIN students " +
            "ON students_courses.student_id = students.student_id " +
            "RIGHT JOIN courses " +
            "ON students_courses.course_id = courses.course_id " +
            "GROUP BY courses.course_id " +
            "ORDER BY courses.course_id ASC";
    private static final String READ_BY_ID =
            "SELECT courses.course_id, courses.course_name, courses.course_description, courses.professor_id, " +
            "string_agg(students.student_id::text, ','), " +
            "string_agg(students.first_name, ','), " +
            "string_agg(students.last_name, ',') " +
            "FROM students_courses " +
            "INNER JOIN students " +
            "ON students_courses.student_id = students.student_id " +
            "RIGHT JOIN courses " +
            "ON students_courses.course_id = courses.course_id " +
            "WHERE courses.course_id = ? " +
            "GROUP BY courses.course_id";
    private static final String UPDATE =
            "UPDATE courses set course_name = ?, course_description = ?, professor_id = ? " +
                    "WHERE course_id = ?";
    private static final String DELETE = "DELETE FROM courses WHERE course_id = ?";
    private static final String FIND_BY_COURSE =
            "SELECT students.student_id, students.first_name, students.last_name, students.group_id " +
            "FROM students_courses " +
            "INNER  JOIN students " +
            "ON students_courses.student_id = students.student_id " +
            "WHERE students_courses.course_id = ?";
    private static final String ADD_STUDENT_TO_COURSE =
            "INSERT INTO students_courses (student_id, course_id) VALUES (?, ?)";
    private static final String DELETE_STUDENT_FROM_COURSE =
            "DELETE FROM students_courses WHERE student_id = ? AND course_id = ?";
    private static final String ADD_PROFESSOR_TO_COURSE =
            "UPDATE courses set professor_id = ? WHERE course_id = ?";
    private static final String DELETE_PROFESSOR_FROM_COURSE =
            "UPDATE courses set professor_id = null WHERE course_id = ?";
    private final Logger logger = LoggerFactory.getLogger(CourseDAO.class);
    private final JdbcTemplate jdbcTemplate;

    @Override
    public Course create(Course course) {
        logger.debug("creating course: {}", course);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
                    PreparedStatement resultSet =
                            connection.prepareStatement(CREATE, new String[] {"course_id"});
                    resultSet.setString(1, course.getCourseName());
                    resultSet.setString(2, course.getDescription());
                    resultSet.setInt(3, course.getProfessorId());
                    return resultSet;
                },
                keyHolder);
        course.setCourseId((Integer) keyHolder.getKey());
        return course;
    }

    @Override
    public List<Course> readAll() {
        logger.debug("reading all courses");
        return jdbcTemplate.query(READ_ALL, (resultSet, rowNum) -> {
            List<Student> students = new ArrayList<>();
            Course course = new Course();
            course.setCourseId(resultSet.getInt("course_id"));
            course.setCourseName(resultSet.getString("course_name"));
            course.setDescription(resultSet.getString("course_description"));
            course.setProfessorId(resultSet.getInt("professor_id"));
            String studentIds = resultSet.getString(5);
            if(studentIds != null) {
                String[] splitStudentIds = studentIds.split(",");
                String firstNames = resultSet.getString(6);
                String[] splitFirstNames = firstNames.split(",");
                String lastNames = resultSet.getString(7);
                String[] splitLastNames = lastNames.split(",");
                for (int i = 0; i < splitStudentIds.length; i++) {
                    Student student = new Student();
                    student.setStudentId(Integer.parseInt(splitStudentIds[i]));
                    student.setFirstName(splitFirstNames[i]);
                    student.setLastName(splitLastNames[i]);
                    students.add(student);
                }
                course.setStudents(students);
            }
            return course;
        });
    }

    @Override
    public Course readByID(Integer courseId) throws EmptyResultDataAccessException {
        logger.debug("reading course with ID: {}", courseId);
        return jdbcTemplate.queryForObject(READ_BY_ID, (resultSet, rowNum) -> {
            List<Student> students = new ArrayList<>();
            Course course = new Course();
            course.setCourseId(resultSet.getInt("course_id"));
            course.setCourseName(resultSet.getString("course_name"));
            course.setDescription(resultSet.getString("course_description"));
            course.setProfessorId(resultSet.getInt("professor_id"));
            String studentIds = resultSet.getString(5);
            if(studentIds != null) {
                String[] splitStudentIds = studentIds.split(",");
                String firstNames = resultSet.getString(6);
                String[] splitFirstNames = firstNames.split(",");
                String lastNames = resultSet.getString(7);
                String[] splitLastNames = lastNames.split(",");
                for (int i = 0; i < splitStudentIds.length; i++) {
                    Student student = new Student();
                    student.setStudentId(Integer.parseInt(splitStudentIds[i]));
                    student.setFirstName(splitFirstNames[i]);
                    student.setLastName(splitLastNames[i]);
                    students.add(student);
                }
                course.setStudents(students);
            }
            return course;
            }, courseId);
    }

    @Override
    public Course update(Course course) throws NoSuchObjectException {
        logger.debug("updating course: {}", course);
        int count = jdbcTemplate.update(connection -> {
            PreparedStatement resultSet =
                    connection.prepareStatement(UPDATE, new String[] {"course_id"});
            resultSet.setString(1, course.getCourseName());
            resultSet.setString(2, course.getDescription());
            resultSet.setInt(3, course.getProfessorId());
            resultSet.setInt(4, course.getCourseId());
            return resultSet;
        });
        if (count == 0) {
            logger.warn("updating course: {} exception: {}", course, "Object not found");
            throw new NoSuchObjectException("Object not found");
        }
        return course;
    }

    @Override
    public void delete(Integer courseId) {
        logger.debug("deleting course with ID: {}", courseId);
        jdbcTemplate.update(DELETE, courseId);
    }

    public List<Student> findByCourse(Integer courseId) {
        logger.debug("finding course by ID: {}", courseId);
        return jdbcTemplate.query(FIND_BY_COURSE, (resultSet, rowNum) -> {
            Student student = new Student();
            student.setStudentId(resultSet.getInt("student_id"));
            student.setFirstName(resultSet.getString("first_name"));
            student.setLastName(resultSet.getString("last_name"));
            student.setGroupId(resultSet.getInt("group_id"));
            return student;
        }, courseId);
    }

    public void addStudentToCourse(Integer studentId, Integer courseId) {
        logger.debug("adding student with ID: {} to course with ID: {}", studentId, courseId);
        jdbcTemplate.update(ADD_STUDENT_TO_COURSE, studentId, courseId);
    }

    public void deleteStudentFromCourse(Integer studentId, Integer courseId) {
        logger.debug("deleting student with ID: {} from course with ID: {}", studentId, courseId);
        jdbcTemplate.update(DELETE_STUDENT_FROM_COURSE, studentId, courseId);
    }

    public void addProfessorToCourse(Integer professorId, Integer courseId) {
        logger.debug("adding professor with ID: {} to course with ID: {}", professorId, courseId);
        jdbcTemplate.update(ADD_PROFESSOR_TO_COURSE, professorId, courseId);
    }

    public void deleteProfessorFromCourse(Integer courseId) {
        logger.debug("deleting professor from course with ID: {}", courseId);
        jdbcTemplate.update(DELETE_PROFESSOR_FROM_COURSE, courseId);
    }
}
