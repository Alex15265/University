package com.foxminded.university.dao;

import com.foxminded.university.dao.entities.LessonTime;
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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
@RequiredArgsConstructor
public class LessonTimeDAO implements DAO<LessonTime,Integer> {
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final String CREATE = "INSERT INTO times (lesson_start, lesson_end) VALUES (?, ?)";
    private static final String READ_ALL = "SELECT * FROM times ORDER BY time_id";
    private static final String READ_BY_ID = "SELECT * FROM times WHERE time_id = ?";
    private static final String UPDATE = "UPDATE times set lesson_start = ?, lesson_end = ? WHERE time_id = ?";
    private static final String DELETE = "DELETE FROM times WHERE time_id = ?";
    private final Logger logger = LoggerFactory.getLogger(LessonTimeDAO.class);
    private final JdbcTemplate jdbcTemplate;

    @Override
    public LessonTime create(LessonTime lessonTime) {
        logger.debug("creating lessonTime: {}", lessonTime);
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
        logger.debug("reading all lessonTimes");
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
    public LessonTime readByID(Integer lessonTimeId) throws EmptyResultDataAccessException {
        logger.debug("reading lessonTime with ID: {}", lessonTimeId);
        return jdbcTemplate.queryForObject(READ_BY_ID, (resultSet, rowNum) -> {
            LessonTime lessonTime = new LessonTime();
            lessonTime.setTimeId(resultSet.getInt("time_id"));
            lessonTime.setLessonStart(LocalDateTime.parse(resultSet.getString("lesson_start"),
                formatter));
            lessonTime.setLessonEnd(LocalDateTime.parse(resultSet.getString("lesson_end"),
                formatter));
            return lessonTime;
            }, lessonTimeId);
    }

    @Override
    public LessonTime update(LessonTime lessonTime) throws NoSuchObjectException {
        logger.debug("updating lessonTime: {}", lessonTime);
        int count = jdbcTemplate.update(connection -> {
            PreparedStatement resultSet =
                    connection.prepareStatement(UPDATE, new String[] {"time_id"});
            resultSet.setString(1, lessonTime.getLessonStart().format(formatter));
            resultSet.setString(2, lessonTime.getLessonEnd().format(formatter));
            resultSet.setInt(3, lessonTime.getTimeId());
            return resultSet;
        });
        if (count == 0) {
            logger.warn("updating lessonTime: {} exception: {}", lessonTime, "Object not found");
            throw new NoSuchObjectException("Object not found");
        }
        return lessonTime;
    }

    @Override
    public void delete(Integer lessonTimeId) {
        logger.debug("deleting lessonTime with ID: {}", lessonTimeId);
        jdbcTemplate.update(DELETE, lessonTimeId);
    }
}
