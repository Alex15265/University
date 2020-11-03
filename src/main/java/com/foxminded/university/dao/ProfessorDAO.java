package com.foxminded.university.dao;

import com.foxminded.university.config.DriverManagerDataSourceInitializer;
import com.foxminded.university.dao.entities.Course;
import com.foxminded.university.dao.entities.Professor;
import org.springframework.beans.factory.annotation.Autowired;
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
public class ProfessorDAO implements DAO<Professor,Integer> {
    private static final String CREATE = "INSERT INTO professors (first_name, last_name) VALUES (?, ?)";
    private static final String READ_ALL =
            "SELECT professors.professor_id, professors.first_name, professors.last_name, " +
            "string_agg(courses.course_id::text, ','), " +
            "string_agg(courses.course_name, ','), " +
            "string_agg(courses.course_description, ',') " +
            "FROM professors " +
            "LEFT JOIN courses " +
            "ON professors.professor_id = courses.professor_id " +
            "GROUP BY professors.professor_id " +
            "ORDER BY professors.professor_id ASC";
    private static final String READ_BY_ID =
            "SELECT professors.professor_id, professors.first_name, professors.last_name, " +
            "string_agg(courses.course_id::text, ','), " +
            "string_agg(courses.course_name, ','), " +
            "string_agg(courses.course_description, ',') " +
            "FROM professors " +
            "LEFT JOIN courses " +
            "ON professors.professor_id = courses.professor_id " +
            "WHERE professors.professor_id = ? " +
            "GROUP BY professors.professor_id";
    private static final String UPDATE = "UPDATE professors set first_name = ?, last_name = ? WHERE professor_id = ?";
    private static final String DELETE = "DELETE FROM professors WHERE professor_id = ?";
    private static final String FIND_BY_PROFESSOR =
            "SELECT courses.course_id, courses.course_name, courses.course_description " +
            "FROM courses " +
            "INNER  JOIN professors " +
            "ON courses.professor_id = professors.professor_id " +
            "WHERE professors.professor_id = ?";
    private static final String ADD_COURSE_TO_PROFESSOR =
            "UPDATE courses set professor_id = ? WHERE course_id = ?";
    private static final String DELETE_COURSE_FROM_PROFESSOR =
            "UPDATE courses set professor_id = null WHERE course_id = ?";
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ProfessorDAO(DriverManagerDataSourceInitializer initializer) {
        jdbcTemplate = initializer.initialize();
    }

    @Override
    public Professor create(Professor professor) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
                    PreparedStatement resultSet =
                            connection.prepareStatement(CREATE, new String[] {"professor_id"});
                    resultSet.setString(1, professor.getFirstName());
                    resultSet.setString(2, professor.getLastName());
                    return resultSet;
                },
                keyHolder);
        professor.setProfessorId((Integer) keyHolder.getKey());
        return professor;
    }

    @Override
    public List<Professor> readAll() {
        return jdbcTemplate.query(READ_ALL, (resultSet, rowNum) -> {
            List<Course> courses = new ArrayList<>();
            Professor professor = new Professor();
            professor.setProfessorId(resultSet.getInt("professor_id"));
            professor.setFirstName(resultSet.getString("first_name"));
            professor.setLastName(resultSet.getString("last_name"));
            String courseIds = resultSet.getString(4);
            if(courseIds != null) {
                String[] splitCourseIds = courseIds.split(",");
                String courseNames = resultSet.getString(5);
                String[] splitCourseNames = courseNames.split(",");
                String courseDescriptions = resultSet.getString(6);
                String[] splitCourseDescriptions = courseDescriptions.split(",");
                for (int i = 0; i < splitCourseIds.length; i++) {
                    Course course = new Course();
                    course.setCourseId(Integer.parseInt(splitCourseIds[i]));
                    course.setCourseName(splitCourseNames[i]);
                    course.setDescription(splitCourseDescriptions[i]);
                    courses.add(course);
                }
                professor.setCourses(courses);
            }
            return professor;
        });
    }

    @Override
    public Professor readByID(Integer id) throws EmptyResultDataAccessException {
        return jdbcTemplate.queryForObject(READ_BY_ID, (resultSet, rowNum) -> {
            List<Course> courses = new ArrayList<>();
            Professor professor = new Professor();
            professor.setProfessorId(resultSet.getInt("professor_id"));
            professor.setFirstName(resultSet.getString("first_name"));
            professor.setLastName(resultSet.getString("last_name"));
            String courseIds = resultSet.getString(4);
            if(courseIds != null) {
                String[] splitCourseIds = courseIds.split(",");
                String courseNames = resultSet.getString(5);
                String[] splitCourseNames = courseNames.split(",");
                String courseDescriptions = resultSet.getString(6);
                String[] splitCourseDescriptions = courseDescriptions.split(",");
                for (int i = 0; i < splitCourseIds.length; i++) {
                    Course course = new Course();
                    course.setCourseId(Integer.parseInt(splitCourseIds[i]));
                    course.setCourseName(splitCourseNames[i]);
                    course.setDescription(splitCourseDescriptions[i]);
                    courses.add(course);
                }
                professor.setCourses(courses);
            }
            return professor;
        }, id);
    }

    @Override
    public Professor update(Professor professor) throws NoSuchObjectException {
        int count = jdbcTemplate.update(connection -> {
            PreparedStatement resultSet =
                    connection.prepareStatement(UPDATE, new String[] {"professor_id"});
            resultSet.setString(1, professor.getFirstName());
            resultSet.setString(2, professor.getLastName());
            resultSet.setInt(3, professor.getProfessorId());
            return resultSet;
        });
        if (count == 0) throw new NoSuchObjectException("Object not found");
        return professor;
    }

    @Override
    public void delete(Integer id) {
        jdbcTemplate.update(DELETE, id);
    }

    public List<Course> findCoursesByProfessor(Integer professorId) {
        return jdbcTemplate.query(FIND_BY_PROFESSOR, (resultSet, rowNum) -> {
            Course course = new Course();
            course.setCourseId(resultSet.getInt("course_id"));
            course.setCourseName(resultSet.getString("course_name"));
            course.setDescription(resultSet.getString("course_description"));
            return course;
        }, professorId);
    }

    public void addCourseToProfessor(Integer courseId, Integer professorId) {
        jdbcTemplate.update(ADD_COURSE_TO_PROFESSOR, professorId, courseId);
    }

    public void deleteCourseFromProfessor(Integer professorId) {
        jdbcTemplate.update(DELETE_COURSE_FROM_PROFESSOR, professorId);
    }
}
