package com.foxminded.university.dao;

import com.foxminded.university.dao.entities.LessonTime;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class LessonTimeDAO implements DAO<LessonTime,Integer> {
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final String UPDATE = "UPDATE times set lesson_start = ?, lesson_end = ? WHERE time_id = ?";
    private static final String READ_BY_ID = "SELECT * FROM times WHERE time_id = ?";
    private static final String READ_ALL = "SELECT * FROM times";
    private static final String CREATE = "INSERT INTO times (lesson_start, lesson_end) VALUES (?, ?)";
    private static final String DELETE = "DELETE FROM times WHERE time_id = ?";
    private final JdbcTemplate jdbcTemplate;

    public LessonTimeDAO(DriverManagerDataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void create(LessonTime lessonTime) {
        jdbcTemplate.update(CREATE, lessonTime.getLessonStart().format(formatter),
                lessonTime.getLessonEnd().format(formatter));
    }

    @Override
    public List<LessonTime> readAll() {
        return jdbcTemplate.query(READ_ALL, (resultSet, rowNum) -> {
            LessonTime lessonTime = new LessonTime();
            lessonTime.setTimeId(resultSet.getInt("time_id"));
            lessonTime.setLessonStart(LocalDateTime.parse(resultSet.getString("lesson_start"),
                formatter));
            lessonTime.setLessonEnd(LocalDateTime.parse(resultSet.getString("lesson_end"),
                formatter));
            return lessonTime;
        });
    }

    @Override
    public LessonTime readByID(Integer id) {
        return jdbcTemplate.queryForObject(READ_BY_ID, (resultSet, rowNum) -> {
            LessonTime lessonTime = new LessonTime();
            lessonTime.setTimeId(resultSet.getInt("time_id"));
            lessonTime.setLessonStart(LocalDateTime.parse(resultSet.getString("lesson_start"),
                formatter));
            lessonTime.setLessonEnd(LocalDateTime.parse(resultSet.getString("lesson_end"),
                formatter));
            return lessonTime;
            }, id);
    }

    @Override
    public void update(LessonTime lessonTime) {
        jdbcTemplate.update(UPDATE, lessonTime.getLessonStart().format(formatter),
            lessonTime.getLessonEnd().format(formatter),
            lessonTime.getTimeId());
    }

    @Override
    public void delete(Integer id) {
        jdbcTemplate.update(DELETE, id);
    }
}
