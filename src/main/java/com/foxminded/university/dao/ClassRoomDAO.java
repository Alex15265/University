package com.foxminded.university.dao;

import com.foxminded.university.dao.entities.ClassRoom;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.util.List;

public class ClassRoomDAO implements DAO<ClassRoom,Integer> {
    private static final String UPDATE = "UPDATE classrooms set room_number = ? WHERE room_id = ?";
    private static final String READ_BY_ID = "SELECT * FROM classrooms WHERE room_id = ?";
    private static final String READ_ALL = "SELECT * FROM classrooms";
    private static final String CREATE = "INSERT INTO classrooms (room_number) VALUES (?)";
    private static final String DELETE = "DELETE FROM classrooms WHERE room_id = ?";
    private final JdbcTemplate jdbcTemplate;

    public ClassRoomDAO(DriverManagerDataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void create(ClassRoom room) {
        jdbcTemplate.update(CREATE, room.getRoomNumber());
    }

    @Override
    public List<ClassRoom> readAll() {
        return jdbcTemplate.query(READ_ALL,
                new BeanPropertyRowMapper<>(ClassRoom.class));
    }

    @Override
    public ClassRoom readByID(Integer id) {
        return jdbcTemplate.queryForObject(READ_BY_ID,
                new Object[] {id}, new BeanPropertyRowMapper<>(ClassRoom.class));
    }

    @Override
    public void update(ClassRoom room) {
        jdbcTemplate.update(UPDATE,
                room.getRoomNumber(), room.getRoomId());
    }

    @Override
    public void delete(Integer id) {
        jdbcTemplate.update(DELETE, id);
    }
}
