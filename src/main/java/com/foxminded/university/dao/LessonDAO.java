package com.foxminded.university.dao;

import com.foxminded.university.dao.entities.*;
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
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class LessonDAO implements DAO<Lesson,Integer> {
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final String CREATE =
            "INSERT INTO lessons (professor_id, course_id, room_id, time_id) VALUES (?, ?, ?, ?)";
    private static final String READ_ALL =
            "SELECT lessons.lesson_id, professors.professor_id, professors.first_name, " +
            "professors.last_name, courses.course_id, courses.course_name, courses.course_description, " +
            "classrooms.room_id, classrooms.room_number, times.time_id, times.lesson_start, times.lesson_end, " +
            "string_agg(groups.group_id::text, ','), " +
            "string_agg(groups.group_name, ',') " +
            "FROM groups_lessons " +
            "INNER JOIN groups " +
            "ON groups.group_id = groups_lessons.group_id " +
            "INNER JOIN lessons " +
            "ON lessons.lesson_id = groups_lessons.lesson_id " +
            "INNER JOIN professors " +
            "ON professors.professor_id = lessons.professor_id " +
            "INNER JOIN courses " +
            "ON courses.course_id = lessons.course_id " +
            "INNER JOIN classrooms " +
            "ON classrooms.room_id = lessons.room_id " +
            "INNER JOIN times " +
            "ON times.time_id = lessons.time_id " +
            "GROUP BY lessons.lesson_id, professors.professor_id, " +
            "courses.course_id, classrooms.room_id, times.time_id " +
            "ORDER BY lessons.lesson_id ASC";
    private static final String READ_BY_ID =
            "SELECT lessons.lesson_id, professors.professor_id, professors.first_name, " +
            "professors.last_name, courses.course_id, courses.course_name, courses.course_description, " +
            "classrooms.room_id, classrooms.room_number, times.time_id, times.lesson_start, times.lesson_end, " +
            "string_agg(groups.group_id::text, ','), " +
            "string_agg(groups.group_name, ',') " +
            "FROM groups_lessons " +
            "INNER JOIN groups " +
            "ON groups.group_id = groups_lessons.group_id " +
            "INNER JOIN lessons " +
            "ON lessons.lesson_id = groups_lessons.lesson_id " +
            "INNER JOIN professors " +
            "ON professors.professor_id = lessons.professor_id " +
            "INNER JOIN courses " +
            "ON courses.course_id = lessons.course_id " +
            "INNER JOIN classrooms " +
            "ON classrooms.room_id = lessons.room_id " +
            "INNER JOIN times " +
            "ON times.time_id = lessons.time_id " +
            "WHERE lessons.lesson_id = ? " +
            "GROUP BY lessons.lesson_id, professors.professor_id, " +
            "courses.course_id, classrooms.room_id, times.time_id";
    private static final String UPDATE = "UPDATE lessons set professor_id = ?, " +
            "course_id = ?, room_id = ?, time_id = ?  WHERE lesson_id = ?";
    private static final String DELETE = "DELETE FROM lessons WHERE lesson_id = ?";
    private static final String FIND_BY_LESSON =
            "SELECT groups.group_id, groups.group_name " +
            "FROM groups_lessons " +
            "INNER JOIN groups " +
            "ON groups_lessons.group_id = groups.group_id " +
            "WHERE groups_lessons.lesson_id = ?";
    private static final String ADD_GROUP_TO_LESSON =
            "INSERT INTO groups_lessons (group_id, lesson_id) VALUES (?, ?)";
    private static final String DELETE_GROUP_FROM_LESSON =
            "DELETE FROM groups_lessons WHERE group_id = ? AND lesson_id = ?";
    private final Logger logger = LoggerFactory.getLogger(LessonDAO.class);
    private final JdbcTemplate jdbcTemplate;

    @Override
    public Lesson create(Lesson lesson) {
        logger.debug("creating lesson: {}", lesson);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
                    PreparedStatement resultSet =
                            connection.prepareStatement(CREATE, new String[] {"lesson_id"});
                    resultSet.setInt(1, lesson.getProfessor().getProfessorId());
                    resultSet.setInt(2, lesson.getCourse().getCourseId());
                    resultSet.setInt(3, lesson.getClassRoom().getRoomId());
                    resultSet.setInt(4, lesson.getTime().getTimeId());
                    return resultSet;
                },
                keyHolder);
        lesson.setLessonId((Integer) keyHolder.getKey());
        return lesson;
    }

    @Override
    public List<Lesson> readAll() {
        logger.debug("reading all lessons");
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
            List<Group> groups = new ArrayList<>();
            String groupIds = resultSet.getString(13);
            String[] splitGroupIds = groupIds.split(",");
            String groupNames = resultSet.getString(14);
            String[] splitGroupNames = groupNames.split(",");
            for (int i = 0; i < splitGroupIds.length; i++) {
                Group group = new Group();
                group.setGroupId(Integer.parseInt(splitGroupIds[i]));
                group.setGroupName(splitGroupNames[i]);
                groups.add(group);
            }
            Lesson lesson = new Lesson();
            lesson.setLessonId(resultSet.getInt("lesson_id"));
            lesson.setProfessor(professor);
            lesson.setCourse(course);
            lesson.setClassRoom(classRoom);
            lesson.setTime(lessonTime);
            lesson.setGroups(groups);
            return lesson;
        });
    }

    @Override
    public Lesson readByID(Integer lessonId) throws EmptyResultDataAccessException {
        logger.debug("reading lesson with ID: {}", lessonId);
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
            List<Group> groups = new ArrayList<>();
            String groupIds = resultSet.getString(13);
            String[] splitGroupIds = groupIds.split(",");
            String groupNames = resultSet.getString(14);
            String[] splitGroupNames = groupNames.split(",");
            for (int i = 0; i < splitGroupIds.length; i++) {
                Group group = new Group();
                group.setGroupId(Integer.parseInt(splitGroupIds[i]));
                group.setGroupName(splitGroupNames[i]);
                groups.add(group);
            }
            Lesson lesson = new Lesson();
            lesson.setLessonId(resultSet.getInt("lesson_id"));
            lesson.setProfessor(professor);
            lesson.setCourse(course);
            lesson.setClassRoom(classRoom);
            lesson.setTime(lessonTime);
            lesson.setGroups(groups);
            return lesson;
        }, lessonId);
    }

    @Override
    public Lesson update(Lesson lesson) throws NoSuchObjectException {
        logger.debug("updating lesson: {}", lesson);
        int count = jdbcTemplate.update(connection -> {
            PreparedStatement resultSet =
                    connection.prepareStatement(UPDATE, new String[] {"lesson_id"});
            resultSet.setInt(1, lesson.getProfessor().getProfessorId());
            resultSet.setInt(2, lesson.getCourse().getCourseId());
            resultSet.setInt(3, lesson.getClassRoom().getRoomId());
            resultSet.setInt(4, lesson.getTime().getTimeId());
            resultSet.setInt(5, lesson.getLessonId());
            return resultSet;
        });
        if (count == 0) {
            logger.warn("updating lesson: {} exception: {}", lesson, "Object not found");
            throw new NoSuchObjectException("Object not found");
        }
        return lesson;
    }

    @Override
    public void delete(Integer lessonId) {
        logger.debug("deleting lesson with ID: {}", lessonId);
        jdbcTemplate.update(DELETE, lessonId);
    }

    public List<Group> readGroupsByLesson(Integer lessonId) {
        logger.debug("reading groups by lesson with ID: {}", lessonId);
        return jdbcTemplate.query(FIND_BY_LESSON, (resultSet, rowNum) -> {
            Group group = new Group();
            group.setGroupId(resultSet.getInt("group_id"));
            group.setGroupName(resultSet.getString("group_name"));
            return group;
        }, lessonId);
    }

    public void addGroupToLesson(Integer groupId, Integer lessonId) {
        logger.debug("adding group with ID: {} to lesson with ID: {}", groupId, lessonId);
        jdbcTemplate.update(ADD_GROUP_TO_LESSON, groupId, lessonId);
    }

    public void deleteGroupFromLesson(Integer groupId, Integer lessonId) {
        logger.debug("deleting group with ID: {} from lesson with ID: {}", groupId, lessonId);
        jdbcTemplate.update(DELETE_GROUP_FROM_LESSON, groupId, lessonId);
    }
}
