package com.foxminded.university.dao;

import com.foxminded.university.dao.entities.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class LessonDAO implements DAO<Lesson,Integer> {
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final String UPDATE = "UPDATE lessons set professor_id = ?, course_id = ?, room_id = ?, time_id = ?  WHERE lesson_id = ?";
    private static final String READ_BY_ID = "SELECT * FROM lessons INNER JOIN professors \n" +
            "ON professors.professor_id = lessons.professor_id INNER JOIN courses ON courses.course_id = lessons.course_id \n" +
            "INNER JOIN classrooms ON classrooms.room_id = lessons.room_id INNER JOIN times \n" +
            "ON times.time_id = lessons.time_id WHERE lesson_id = ?";
    private static final String READ_ALL = "SELECT * FROM lessons INNER JOIN professors \n" +
            "ON professors.professor_id = lessons.professor_id INNER JOIN courses ON courses.course_id = lessons.course_id \n" +
            "INNER JOIN classrooms ON classrooms.room_id = lessons.room_id INNER JOIN times \n" +
            "ON times.time_id = lessons.time_id";
    private static final String CREATE = "INSERT INTO lessons (professor_id, course_id, room_id, time_id) VALUES (?, ?, ?, ?)";
    private static final String DELETE = "DELETE FROM lessons WHERE lesson_id = ?";
    private final JdbcTemplate jdbcTemplate;

    public LessonDAO(DriverManagerDataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void create(Lesson lesson) {
        jdbcTemplate.update(CREATE, lesson.getProfessor().getProfessorId(), lesson.getCourse().getCourseId(),
                lesson.getClassRoom().getRoomId(), lesson.getTime().getTimeId());
    }

    @Override
    public List<Lesson> readAll() {
        return jdbcTemplate.query(READ_ALL, (resultSet, rowNum) -> {
            Professor professor = new Professor();
            professor.setProfessorId(resultSet.getInt("professor_id"));
            professor.setFirstName(resultSet.getString("first_name"));
            professor.setLastName(resultSet.getString("last_name"));
            Course course = new Course();
            course.setCourseId(resultSet.getInt("course_id"));
            course.setCourseName(resultSet.getString("course_name"));
            course.setDescription(resultSet.getString("course_description"));
            ClassRoom classRoom = new ClassRoom();
            classRoom.setRoomId(resultSet.getInt("room_id"));
            classRoom.setRoomNumber(resultSet.getInt("room_number"));
            LessonTime lessonTime = new LessonTime();
            lessonTime.setTimeId(resultSet.getInt("time_id"));
            lessonTime.setLessonStart(LocalDateTime.parse(resultSet.getString("lesson_start"),
                    formatter));
            lessonTime.setLessonEnd(LocalDateTime.parse(resultSet.getString("lesson_end"),
                    formatter));
            Lesson lesson = new Lesson();
            lesson.setLessonId(resultSet.getInt("lesson_id"));
            lesson.setProfessor(professor);
            lesson.setCourse(course);
            lesson.setClassRoom(classRoom);
            lesson.setTime(lessonTime);
            return lesson;
        });
    }

    @Override
    public Lesson readByID(Integer id) {
        return jdbcTemplate.queryForObject(READ_BY_ID, (resultSet, rowNum) -> {
            Professor professor = new Professor();
            professor.setProfessorId(resultSet.getInt("professor_id"));
            professor.setFirstName(resultSet.getString("first_name"));
            professor.setLastName(resultSet.getString("last_name"));
            Course course = new Course();
            course.setCourseId(resultSet.getInt("course_id"));
            course.setCourseName(resultSet.getString("course_name"));
            course.setDescription(resultSet.getString("course_description"));
            ClassRoom classRoom = new ClassRoom();
            classRoom.setRoomId(resultSet.getInt("room_id"));
            classRoom.setRoomNumber(resultSet.getInt("room_number"));
            LessonTime lessonTime = new LessonTime();
            lessonTime.setTimeId(resultSet.getInt("time_id"));
            lessonTime.setLessonStart(LocalDateTime.parse(resultSet.getString("lesson_start"),
                    formatter));
            lessonTime.setLessonEnd(LocalDateTime.parse(resultSet.getString("lesson_end"),
                    formatter));
            Lesson lesson = new Lesson();
            lesson.setLessonId(resultSet.getInt("lesson_id"));
            lesson.setProfessor(professor);
            lesson.setCourse(course);
            lesson.setClassRoom(classRoom);
            lesson.setTime(lessonTime);
            return lesson;
        }, id);
    }

    @Override
    public void update(Lesson lesson) {
        jdbcTemplate.update(UPDATE,
                lesson.getProfessor().getProfessorId(), lesson.getCourse().getCourseId(),
                lesson.getClassRoom().getRoomId(), lesson.getTime().getTimeId(), lesson.getLessonId());
    }

    @Override
    public void delete(Integer id) {
        jdbcTemplate.update(DELETE, id);
    }
}
