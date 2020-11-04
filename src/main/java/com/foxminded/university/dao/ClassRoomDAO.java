package com.foxminded.university.dao;

import com.foxminded.university.config.DriverManagerDataSourceInitializer;
import com.foxminded.university.dao.entities.ClassRoom;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.rmi.NoSuchObjectException;
import java.sql.PreparedStatement;
import java.util.List;

@Component
public class ClassRoomDAO implements DAO<ClassRoom,Integer> {
    private static final String CREATE = "INSERT INTO classrooms (room_number) VALUES (?)";
    private static final String READ_ALL = "SELECT * FROM classrooms";
    private static final String READ_BY_ID = "SELECT * FROM classrooms WHERE room_id = ?";
    private static final String UPDATE = "UPDATE classrooms set room_number = ? WHERE room_id = ?";
    private static final String DELETE = "DELETE FROM classrooms WHERE room_id = ?";
    private final Logger logger = LoggerFactory.getLogger(ClassRoomDAO.class);
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ClassRoomDAO(DriverManagerDataSourceInitializer initializer) {
        jdbcTemplate = initializer.initialize();
    }

    @Override
    public ClassRoom create(ClassRoom room) {
        logger.debug("creating classroom: {}", room);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
                    PreparedStatement resultSet =
                            connection.prepareStatement(CREATE, new String[] {"room_id"});
                    resultSet.setInt(1, room.getRoomNumber());
                    return resultSet;
                },
                keyHolder);
        room.setRoomId((Integer) keyHolder.getKey());
        return room;
    }

    @Override
    public List<ClassRoom> readAll() {
        logger.debug("reading all rooms");
        return jdbcTemplate.query(READ_ALL,
                new BeanPropertyRowMapper<>(ClassRoom.class));
    }

    @Override
    public ClassRoom readByID(Integer classRoomId) throws EmptyResultDataAccessException {
        logger.debug("reading room with ID: {}", classRoomId);
        return jdbcTemplate.queryForObject(READ_BY_ID,
                new Object[] {classRoomId}, new BeanPropertyRowMapper<>(ClassRoom.class));
    }

    @Override
    public ClassRoom update(ClassRoom room) throws NoSuchObjectException{
        logger.debug("updating room: {}", room);
        int count = jdbcTemplate.update(connection -> {
                    PreparedStatement resultSet =
                            connection.prepareStatement(UPDATE, new String[] {"room_id"});
                    resultSet.setInt(1, room.getRoomNumber());
                    resultSet.setInt(2, room.getRoomId());
                    return resultSet;
                });
        if (count == 0) {
            logger.warn("updating room: {} exception: {}", room, "Object not found");
            throw new NoSuchObjectException("Object not found");
        }
        return room;
    }

    @Override
    public void delete(Integer classRoomId) {
        logger.debug("updating room with ID: {}", classRoomId);
        jdbcTemplate.update(DELETE, classRoomId);
    }
}
