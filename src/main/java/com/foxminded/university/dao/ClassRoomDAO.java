package com.foxminded.university.dao;

import com.foxminded.university.config.DriverManagerDataSourceInitializer;
import com.foxminded.university.dao.entities.ClassRoom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.sql.PreparedStatement;
import java.util.List;

@Component
public class ClassRoomDAO implements DAO<ClassRoom,Integer> {
    private static final String UPDATE = "UPDATE classrooms set room_number = ? WHERE room_id = ?";
    private static final String READ_BY_ID = "SELECT * FROM classrooms WHERE room_id = ?";
    private static final String READ_ALL = "SELECT * FROM classrooms";
    private static final String CREATE = "INSERT INTO classrooms (room_number) VALUES (?)";
    private static final String DELETE = "DELETE FROM classrooms WHERE room_id = ?";
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ClassRoomDAO(DriverManagerDataSourceInitializer initializer) {
        jdbcTemplate = new JdbcTemplate(initializer.initialize());
    }

    @Override
    public ClassRoom create(ClassRoom room) {
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
        return jdbcTemplate.query(READ_ALL,
                new BeanPropertyRowMapper<>(ClassRoom.class));
    }

    @Override
    public ClassRoom readByID(Integer id) throws EmptyResultDataAccessException {
        return jdbcTemplate.queryForObject(READ_BY_ID,
                new Object[] {id}, new BeanPropertyRowMapper<>(ClassRoom.class));
    }

    @Override
    public ClassRoom update(ClassRoom room) throws FileNotFoundException{
        int count = jdbcTemplate.update(connection -> {
                    PreparedStatement resultSet =
                            connection.prepareStatement(UPDATE, new String[] {"room_id"});
                    resultSet.setInt(1, room.getRoomNumber());
                    resultSet.setInt(2, room.getRoomId());
                    return resultSet;
                });
        if (count == 0) throw new FileNotFoundException();
        return room;
    }

    @Override
    public void delete(Integer id) {
        jdbcTemplate.update(DELETE, id);
    }
}
