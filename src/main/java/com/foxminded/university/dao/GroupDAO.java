package com.foxminded.university.dao;

import com.foxminded.university.dao.entities.Group;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.util.List;

public class GroupDAO implements DAO<Group,Integer> {
    private static final String UPDATE = "UPDATE groups set group_name = ? WHERE group_id = ?";
    private static final String READ_BY_ID = "SELECT * FROM groups WHERE group_id = ?";
    private static final String READ_ALL = "SELECT * FROM groups";
    private static final String CREATE = "INSERT INTO groups (group_name) VALUES (?)";
    private static final String DELETE = "DELETE FROM groups WHERE group_id = ?";
    private final JdbcTemplate jdbcTemplate;

    public GroupDAO(DriverManagerDataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void create(Group group) {
        jdbcTemplate.update(CREATE, group.getGroupName());
    }

    @Override
    public List<Group> readAll() {
        return jdbcTemplate.query(READ_ALL, (resultSet, rowNum) -> {
            Group group = new Group();
            group.setGroupId(resultSet.getInt("group_id"));
            group.setGroupName(resultSet.getString("group_name"));
            return group;
        });
    }

    @Override
    public Group readByID(Integer id) {
        return jdbcTemplate.queryForObject(READ_BY_ID, (resultSet, rowNum) -> {
            Group group = new Group();
            group.setGroupId(resultSet.getInt("group_id"));
            group.setGroupName(resultSet.getString("group_name"));
            return group;
            }, id);
    }

    @Override
    public void update(Group group) {
        jdbcTemplate.update(UPDATE, group.getGroupName(), group.getGroupId());
    }

    @Override
    public void delete(Integer id) {
        jdbcTemplate.update(DELETE, id);
    }
}
