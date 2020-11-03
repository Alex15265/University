package com.foxminded.university.dao;

import com.foxminded.university.config.DriverManagerDataSourceInitializer;
import com.foxminded.university.dao.entities.Group;
import com.foxminded.university.dao.entities.Student;
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
public class GroupDAO implements DAO<Group,Integer> {
    private static final String CREATE = "INSERT INTO groups (group_name) VALUES (?)";
    private static final String READ_ALL =
            "SELECT groups.group_id, groups.group_name, " +
            "string_agg(students.student_id::text, ','), " +
            "string_agg(students.first_name, ','), " +
            "string_agg(students.last_name, ',') " +
            "FROM groups " +
            "LEFT JOIN students " +
            "ON groups.group_id = students.group_id " +
            "GROUP BY groups.group_id " +
            "ORDER BY groups.group_id ASC";
    private static final String READ_BY_ID =
            "SELECT groups.group_id, groups.group_name, " +
            "string_agg(students.student_id::text, ','), " +
            "string_agg(students.first_name, ','), " +
            "string_agg(students.last_name, ',') " +
            "FROM groups " +
            "LEFT JOIN students " +
            "ON groups.group_id = students.group_id " +
            "WHERE groups.group_id = ? " +
            "GROUP BY groups.group_id";
    private static final String UPDATE = "UPDATE groups set group_name = ? WHERE group_id = ?";
    private static final String DELETE = "DELETE FROM groups WHERE group_id = ?";
    private static final String FIND_BY_GROUP =
            "SELECT students.student_id, students.first_name, students.last_name " +
            "FROM groups " +
            "INNER  JOIN students " +
            "ON groups.group_id = students.group_id " +
            "WHERE groups.group_id = ?";
    private static final String ADD_STUDENT_TO_GROUP =
            "UPDATE students set group_id = ? WHERE student_id = ?";
    private static final String DELETE_STUDENT_FROM_GROUP =
            "UPDATE students set group_id = null WHERE student_id = ?";
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public GroupDAO(DriverManagerDataSourceInitializer initializer) {
        jdbcTemplate = initializer.initialize();
    }

    @Override
    public Group create(Group group) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
                    PreparedStatement resultSet =
                            connection.prepareStatement(CREATE, new String[] {"group_id"});
                    resultSet.setString(1, group.getGroupName());
                    return resultSet;
                },
                keyHolder);
        group.setGroupId((Integer) keyHolder.getKey());
        return group;
    }

    @Override
    public List<Group> readAll() {
        return jdbcTemplate.query(READ_ALL, (resultSet, rowNum) -> {
            List<Student> students = new ArrayList<>();
            Group group = new Group();
            group.setGroupId(resultSet.getInt("group_id"));
            group.setGroupName(resultSet.getString("group_name"));
            String studentIds = resultSet.getString(3);
            if(studentIds != null) {
                String[] splitStudentIds = studentIds.split(",");
                String firstNames = resultSet.getString(4);
                String[] splitFirstNames = firstNames.split(",");
                String lastNames = resultSet.getString(5);
                String[] splitLastNames = lastNames.split(",");
                for (int i = 0; i < splitStudentIds.length; i++) {
                    Student student = new Student();
                    student.setStudentId(Integer.parseInt(splitStudentIds[i]));
                    student.setFirstName(splitFirstNames[i]);
                    student.setLastName(splitLastNames[i]);
                    students.add(student);
                }
                group.setStudents(students);
            }
            return group;
        });
    }

    @Override
    public Group readByID(Integer id) throws EmptyResultDataAccessException {
        return jdbcTemplate.queryForObject(READ_BY_ID, (resultSet, rowNum) -> {
            List<Student> students = new ArrayList<>();
            Group group = new Group();
            group.setGroupId(resultSet.getInt("group_id"));
            group.setGroupName(resultSet.getString("group_name"));
            String studentIds = resultSet.getString(3);
            if(studentIds != null) {
                String[] splitStudentIds = studentIds.split(",");
                String firstNames = resultSet.getString(4);
                String[] splitFirstNames = firstNames.split(",");
                String lastNames = resultSet.getString(5);
                String[] splitLastNames = lastNames.split(",");
                for (int i = 0; i < splitStudentIds.length; i++) {
                    Student student = new Student();
                    student.setStudentId(Integer.parseInt(splitStudentIds[i]));
                    student.setFirstName(splitFirstNames[i]);
                    student.setLastName(splitLastNames[i]);
                    students.add(student);
                }
                group.setStudents(students);
            }
            return group;
            }, id);
    }

    @Override
    public Group update(Group group) throws NoSuchObjectException {
        int count = jdbcTemplate.update(connection -> {
            PreparedStatement resultSet =
                    connection.prepareStatement(UPDATE, new String[] {"group_id"});
            resultSet.setString(1, group.getGroupName());
            resultSet.setInt(2, group.getGroupId());
            return resultSet;
        });
        if (count == 0) throw new NoSuchObjectException("Object not found");
        return group;
    }

    @Override
    public void delete(Integer id) {
        jdbcTemplate.update(DELETE, id);
    }

    public List<Student> findByGroup(Integer groupId) {
        return jdbcTemplate.query(FIND_BY_GROUP, (resultSet, rowNum) -> {
            Student student = new Student();
            student.setStudentId(resultSet.getInt("student_id"));
            student.setFirstName(resultSet.getString("first_name"));
            student.setLastName(resultSet.getString("last_name"));
            return student;
        }, groupId);
    }

    public void addStudentToGroup(Integer studentId, Integer groupId) {
        jdbcTemplate.update(ADD_STUDENT_TO_GROUP, groupId, studentId);
    }

    public void deleteStudentFromGroup(Integer studentId) {
        jdbcTemplate.update(DELETE_STUDENT_FROM_GROUP, studentId);
    }
}
