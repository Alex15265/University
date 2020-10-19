package com.foxminded.university.dao;

import com.foxminded.university.dao.entities.LessonTime;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.PreparedStatement;
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
    public LessonTime create(LessonTime lessonTime) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
                    PreparedStatement resultSet =
                            connection.prepareStatement(CREATE, new String[] {"time_id"});
                    resultSet.setString(1, lessonTime.getLessonStart().format(formatter));
                    resultSet.setString(2, lessonTime.getLessonEnd().format(formatter));
                    return resultSet;
                },
                keyHolder);
        lessonTime.setTimeId((Integer) keyHolder.getKey());
        return lessonTime;
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
    public LessonTime update(LessonTime lessonTime) {
        jdbcTemplate.update(UPDATE, lessonTime.getLessonStart().format(formatter),
            lessonTime.getLessonEnd().format(formatter),
            lessonTime.getTimeId());
        return lessonTime;
    }

    @Override
    public void delete(Integer id) {
        jdbcTemplate.update(DELETE, id);
    }
}
