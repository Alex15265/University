package com.foxminded.university.service;

import com.foxminded.university.entities.*;
import com.foxminded.university.repositories.GroupRepository;
import com.foxminded.university.repositories.LessonRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.dao.EmptyResultDataAccessException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class LessonServiceTest {
    LessonRepository mockedLessonRep;
    GroupRepository mockedGroupRep;
    ProfessorService mockedProfessorService;
    CourseService mockedCourseService;
    ClassRoomService mockedClassRoomService;
    LessonTimeService mockedLessonTimeService;
    LessonService lessonService;

    @BeforeEach
    void init() {
        mockedLessonRep = mock(LessonRepository.class);
        mockedProfessorService = mock(ProfessorService.class);
        mockedCourseService = mock(CourseService.class);
        mockedClassRoomService = mock(ClassRoomService.class);
        mockedLessonTimeService = mock(LessonTimeService.class);
        lessonService = new LessonService(mockedLessonRep, mockedGroupRep, mockedProfessorService, mockedCourseService,
                mockedClassRoomService, mockedLessonTimeService);
    }

    @Test
    void create() {
        Professor professor = new Professor();
        professor.setFirstName("John");
        professor.setLastName("Smith");
        Course course = new Course();
        course.setCourseName("History");
        course.setDescription("History Description");
        ClassRoom classRoom = new ClassRoom();
        classRoom.setRoomNumber(23);
        LessonTime lessonTime = new LessonTime();
        lessonTime.setLessonStart(LocalDateTime.of(2020, 9, 1, 8, 0));
        lessonTime.setLessonEnd(LocalDateTime.of(2020, 9, 1, 9, 30));

        Lesson lesson = new Lesson();
        lesson.setProfessor(professor);
        lesson.setCourse(course);
        lesson.setClassRoom(classRoom);
        lesson.setTime(lessonTime);

        when(mockedLessonRep.save(lesson)).thenReturn(lesson);
        when(mockedProfessorService.readById(1)).thenReturn(professor);
        when(mockedCourseService.readByID(4)).thenReturn(course);
        when(mockedClassRoomService.readByID(2)).thenReturn(classRoom);
        when(mockedLessonTimeService.readByID(3)).thenReturn(lessonTime);

        assertEquals(lesson, lessonService.create(1,4,2,3));
        assertEquals(professor, lessonService.create(1,4,2,3).getProfessor());
        assertEquals(course, lessonService.create(1,4,2,3).getCourse());
        assertEquals(classRoom, lessonService.create(1,4,2,3).getClassRoom());
        assertEquals(lessonTime, lessonService.create(1,4,2,3).getTime());

        verify(mockedLessonRep, times(5)).save(lesson);
        verify(mockedProfessorService, times(5)).readById(1);
        verify(mockedCourseService, times(5)).readByID(4);
        verify(mockedClassRoomService, times(5)).readByID(2);
        verify(mockedLessonTimeService, times(5)).readByID(3);
    }

    @Test
    void readAll() {
        List<Lesson> lessons = new ArrayList<>();

        Professor professor1 = new Professor();
        professor1.setFirstName("John");
        professor1.setLastName("Smith");
        Professor professor2 = new Professor();
        professor2.setFirstName("Alex");
        professor2.setLastName("Belyaev");
        Course course1 = new Course();
        course1.setCourseName("History");
        course1.setDescription("History Description");
        Course course2 = new Course();
        course2.setCourseName("Robotics");
        course2.setDescription("Robotics Description");
        ClassRoom classRoom1 = new ClassRoom();
        classRoom1.setRoomNumber(23);
        ClassRoom classRoom2 = new ClassRoom();
        classRoom2.setRoomNumber(2);
        LessonTime lessonTime1 = new LessonTime();
        lessonTime1.setLessonStart(LocalDateTime.of(2020, 9, 1, 8, 0));
        lessonTime1.setLessonEnd(LocalDateTime.of(2020, 9, 1, 9, 30));
        LessonTime lessonTime2 = new LessonTime();
        lessonTime2.setLessonStart(LocalDateTime.of(2020, 9, 1, 11, 0));
        lessonTime2.setLessonEnd(LocalDateTime.of(2020, 9, 1, 12, 30));
        Group group1 = new Group();
        group1.setGroupName("ac-18");
        Group group2 = new Group();
        group2.setGroupName("ab-21");
        List<Group> groups1 = new ArrayList<>();
        groups1.add(group1);
        List<Group> groups2 = new ArrayList<>();
        groups2.add(group2);
        List<Group> groups3 = new ArrayList<>();
        groups3.add(group1);
        groups3.add(group2);

        Lesson lesson1 = new Lesson();
        lesson1.setLessonId(1);
        lesson1.setProfessor(professor1);
        lesson1.setCourse(course1);
        lesson1.setClassRoom(classRoom1);
        lesson1.setTime(lessonTime1);
        lesson1.setGroups(groups1);

        Lesson lesson2 = new Lesson();
        lesson2.setLessonId(2);
        lesson2.setProfessor(professor2);
        lesson2.setCourse(course2);
        lesson2.setClassRoom(classRoom2);
        lesson2.setTime(lessonTime2);
        lesson2.setGroups(groups2);

        Lesson lesson3 = new Lesson();
        lesson3.setLessonId(3);
        lesson3.setProfessor(professor1);
        lesson3.setCourse(course2);
        lesson3.setClassRoom(classRoom2);
        lesson3.setTime(lessonTime1);
        lesson3.setGroups(groups3);

        lessons.add(lesson1);
        lessons.add(lesson2);
        lessons.add(lesson3);

        when(mockedLessonRep.findByOrderByLessonIdAsc()).thenReturn(lessons);

        assertEquals(lessons, lessonService.readAll());
        assertEquals(lesson1, lessonService.readAll().get(0));
        assertEquals(lesson2, lessonService.readAll().get(1));
        assertEquals(lesson3, lessonService.readAll().get(2));
        assertEquals(groups3, lessonService.readAll().get(2).getGroups());

        verify(mockedLessonRep, times(5)).findByOrderByLessonIdAsc();
    }

    @Test
    void readById() {
        Professor professor = new Professor();
        professor.setFirstName("John");
        professor.setLastName("Smith");
        Course course = new Course();
        course.setCourseName("History");
        course.setDescription("History Description");
        ClassRoom classRoom = new ClassRoom();
        classRoom.setRoomNumber(23);
        LessonTime lessonTime = new LessonTime();
        lessonTime.setLessonStart(LocalDateTime.of(2020, 9, 1, 8, 0));
        lessonTime.setLessonEnd(LocalDateTime.of(2020, 9, 1, 9, 30));
        Group group1 = new Group();
        group1.setGroupName("ac-18");
        Group group2 = new Group();
        group2.setGroupName("ab-21");
        List<Group> groups = new ArrayList<>();
        groups.add(group1);
        groups.add(group2);

        Lesson lesson = new Lesson();
        lesson.setLessonId(1);
        lesson.setProfessor(professor);
        lesson.setCourse(course);
        lesson.setClassRoom(classRoom);
        lesson.setTime(lessonTime);
        lesson.setGroups(groups);

        when(mockedLessonRep.findById(1)).thenReturn(java.util.Optional.of(lesson));

        assertEquals(lesson, lessonService.readById(1));
        assertEquals(groups, lessonService.readById(1).getGroups());

        verify(mockedLessonRep, times(2)).findById(1);
    }

    @Test
    void update() {
        Professor professor = new Professor();
        professor.setProfessorId(2);
        professor.setFirstName("John");
        professor.setLastName("Smith");
        Course course = new Course();
        course.setCourseId(1);
        course.setCourseName("History");
        course.setDescription("History Description");
        ClassRoom classRoom = new ClassRoom();
        classRoom.setRoomId(3);
        classRoom.setRoomNumber(23);
        LessonTime lessonTime = new LessonTime();
        lessonTime.setTimeId(1);
        lessonTime.setLessonStart(LocalDateTime.of(2020, 9, 1, 8, 0));
        lessonTime.setLessonEnd(LocalDateTime.of(2020, 9, 1, 9, 30));

        Lesson lesson = new Lesson();
        lesson.setLessonId(7);
        lesson.setProfessor(professor);
        lesson.setCourse(course);
        lesson.setClassRoom(classRoom);
        lesson.setTime(lessonTime);

        when(mockedLessonRep.save(lesson)).thenReturn(lesson);
        when(mockedProfessorService.readById(2)).thenReturn(professor);
        when(mockedCourseService.readByID(1)).thenReturn(course);
        when(mockedClassRoomService.readByID(3)).thenReturn(classRoom);
        when(mockedLessonTimeService.readByID(1)).thenReturn(lessonTime);
        when(mockedLessonRep.existsById(7)).thenReturn(true);

        assertEquals(lesson, lessonService.update(7,2,1,3, 1));
        assertEquals(professor, lessonService.update(7,2,1,3, 1).getProfessor());
        assertEquals(course, lessonService.update(7,2,1,3, 1).getCourse());
        assertEquals(classRoom, lessonService.update(7,2,1,3, 1).getClassRoom());
        assertEquals(lessonTime, lessonService.update(7,2,1,3, 1).getTime());

        verify(mockedLessonRep, times(5)).save(lesson);
        verify(mockedProfessorService, times(5)).readById(2);
        verify(mockedCourseService, times(5)).readByID(1);
        verify(mockedClassRoomService, times(5)).readByID(3);
        verify(mockedLessonTimeService, times(5)).readByID(1);
    }

    @Test
    void delete() {
        when(mockedLessonRep.existsById(1)).thenReturn(true);

        lessonService.delete(1);

        verify(mockedLessonRep, times(1)).deleteById(1);
    }

    @Test
    void readByID_ShouldThrowExceptionWhenInputIsNonExistentID() {
        when(mockedLessonRep.findById(1234)).thenThrow(EmptyResultDataAccessException.class);
        Assertions.assertThrows(EmptyResultDataAccessException.class, () ->
                lessonService.readById(1234));
    }

    @Test
    void update_ShouldThrowExceptionWhenInputIsNonExistentID() {
        when(mockedLessonRep.save(anyObject())).thenThrow(EmptyResultDataAccessException.class);
        Assertions.assertThrows(EmptyResultDataAccessException.class, () ->
                lessonService.update(1, 1, 1, 1, 1));
    }
}