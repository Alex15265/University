package com.foxminded.university.service;

import com.foxminded.university.entities.Group;
import com.foxminded.university.entities.Lesson;
import com.foxminded.university.repositories.GroupRepository;
import com.foxminded.university.repositories.LessonRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LessonService {
    private final LessonRepository lessonRepository;
    private final GroupRepository groupRepository;
    private final ProfessorService professorService;
    private final CourseService courseService;
    private final ClassRoomService classRoomService;
    private final LessonTimeService lessonTimeService;
    private final Logger logger = LoggerFactory.getLogger(LessonService.class);

    public Lesson create(Integer professorId, Integer courseId, Integer roomId, Integer timeId) {
        logger.debug("creating lesson with professorId: {}, courseId: {}, roomId: {}, timeId: {}",
                professorId, courseId, roomId, timeId);
        Lesson lesson = new Lesson();
        lesson.setProfessor(professorService.readById(professorId));
        lesson.setCourse(courseService.readByID(courseId));
        lesson.setClassRoom(classRoomService.readByID(roomId));
        lesson.setTime(lessonTimeService.readByID(timeId));
        if (lessonRepository.findByProfessorAndTime(lesson.getProfessor(), lesson.getTime()) != null) {
            throw new IllegalArgumentException("Lesson already exist");
        }
        return lessonRepository.save(lesson);
    }

    public List<Lesson> readAll() {
        logger.debug("reading all lessons");
        return lessonRepository.findByOrderByLessonIdAsc();
    }

    public Lesson readById(Integer lessonId) {
        logger.debug("reading lesson with ID: {}", lessonId);
        Optional<Lesson> lessonOptional = lessonRepository.findById(lessonId);
        if (!lessonOptional.isPresent()) {
            throw new EmptyResultDataAccessException("Lesson not found", 1);
        }
        return lessonOptional.get();
    }

    public Lesson update(Integer lessonId, Integer professorId, Integer courseId, Integer roomId, Integer timeId) {
        logger.debug("updating lesson with ID: {}, new professorID: {}, courseID: {}, roomID: {} and timeID: {}",
                lessonId, professorId, courseId, roomId, timeId);
        if (!lessonRepository.existsById(lessonId)) {
            throw new EmptyResultDataAccessException("Lesson not found", 1);
        }
        Lesson lesson = new Lesson();
        lesson.setLessonId(lessonId);
        lesson.setProfessor(professorService.readById(professorId));
        lesson.setCourse(courseService.readByID(courseId));
        lesson.setClassRoom(classRoomService.readByID(roomId));
        lesson.setTime(lessonTimeService.readByID(timeId));
        return lessonRepository.save(lesson);
    }

    public void delete(Integer lessonId) {
        logger.debug("deleting lesson with ID: {}", lessonId);
        if (!lessonRepository.existsById(lessonId)) {
            throw new EmptyResultDataAccessException("Lesson not found", 1);
        }
        lessonRepository.deleteById(lessonId);
    }

    public void addGroupToLesson(Integer groupId, Integer lessonId) {
        logger.debug("adding group with ID: {} to lesson with ID: {}", groupId, lessonId);
        Optional<Group> groupOptional = groupRepository.findById(groupId);
        if (!groupOptional.isPresent()) {
            throw new EmptyResultDataAccessException("Group not found", 1);
        }
        Group group = groupOptional.get();
        Optional<Lesson> lessonOptional = lessonRepository.findById(lessonId);
        if (!lessonOptional.isPresent()) {
            throw new EmptyResultDataAccessException("Lesson not found", 1);
        }
        Lesson lesson = lessonOptional.get();
        lesson.getGroups().add(group);
        lessonRepository.save(lesson);
    }

    public void deleteGroupFromLesson(Integer groupId, Integer lessonId) {
        logger.debug("deleting group with ID: {} from lesson with ID: {}", groupId, lessonId);
        Optional<Group> groupOptional = groupRepository.findById(groupId);
        if (!groupOptional.isPresent()) {
            throw new EmptyResultDataAccessException("Group not found", 1);
        }
        Group group = groupOptional.get();
        Optional<Lesson> lessonOptional = lessonRepository.findById(lessonId);
        if (!lessonOptional.isPresent()) {
            throw new EmptyResultDataAccessException("Lesson not found", 1);
        }
        Lesson lesson = lessonOptional.get();
        lesson.getGroups().remove(group);
        lessonRepository.save(lesson);
    }
}
