package com.foxminded.university.service;

import com.foxminded.university.dao.CourseDAO;
import com.foxminded.university.dao.GroupDAO;
import com.foxminded.university.dao.entities.*;
import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TimetableServiceTest {
    LessonService mockedLessonService;
    TimetableService timetableService;
    static List<Lesson> lessons;

    private static Professor professor1;
    private static Professor professor2;
    private static Course course1;
    private static Course course2;
    private static Course course3;
    private static Course course4;
    private static Group group1;
    private static Group group2;
    private static Group group3;
    private static Group group4;

    @BeforeEach
    void init() {
        mockedLessonService = mock(LessonService.class);
        timetableService = new TimetableService(mockedLessonService);
    }

    @BeforeAll
    static void initListOfLessons() {
        List<Group> groups1 = new ArrayList<>();
        List<Group> groups2 = new ArrayList<>();
        List<Group> groups3 = new ArrayList<>();
        List<Group> groups4 = new ArrayList<>();
        lessons = new ArrayList<>();
        professor1 = new Professor();
        professor1.setProfessorId(1);
        professor2 = new Professor();
        professor2.setProfessorId(2);
        course1 = new Course();
        course1.setCourseId(1);
        course2 = new Course();
        course2.setCourseId(2);
        course3 = new Course();
        course3.setCourseId(3);
        course4 = new Course();
        course4.setCourseId(4);
        group1 = new Group();
        group1.setGroupId(1);
        group2 = new Group();
        group2.setGroupId(2);
        group3 = new Group();
        group3.setGroupId(3);
        group4 = new Group();
        group4.setGroupId(4);
        groups1.add(group1);
        groups1.add(group2);
        groups2.add(group3);
        groups2.add(group4);
        groups3.add(group1);
        groups3.add(group4);
        groups4.add(group2);
        groups4.add(group3);
        LessonTime lessonTime1 = new LessonTime();
        lessonTime1.setLessonStart(LocalDateTime.of(2020, 9, 5, 8, 0));
        LessonTime lessonTime2 = new LessonTime();
        lessonTime2.setLessonStart(LocalDateTime.of(2020, 9, 5, 9, 45));
        LessonTime lessonTime3 = new LessonTime();
        lessonTime3.setLessonStart(LocalDateTime.of(2020, 9, 14, 9, 45));
        LessonTime lessonTime4 = new LessonTime();
        lessonTime4.setLessonStart(LocalDateTime.of(2020, 9, 14, 10, 0));
        LessonTime lessonTime5 = new LessonTime();
        lessonTime5.setLessonStart(LocalDateTime.of(2020, 9, 14, 11, 30));
        LessonTime lessonTime6 = new LessonTime();
        lessonTime6.setLessonStart(LocalDateTime.of(2020, 10, 19, 11, 0));
        LessonTime lessonTime7 = new LessonTime();
        lessonTime7.setLessonStart(LocalDateTime.of(2020, 10, 27, 8, 0));
        LessonTime lessonTime8 = new LessonTime();
        lessonTime8.setLessonStart(LocalDateTime.of(2020, 11, 11, 9, 45));

        Lesson lesson1 = new Lesson();
        lesson1.setProfessor(professor1);
        lesson1.setCourse(course1);
        lesson1.setTime(lessonTime1);
        lesson1.setGroups(groups1);

        Lesson lesson2 = new Lesson();
        lesson2.setProfessor(professor2);
        lesson2.setCourse(course3);
        lesson2.setTime(lessonTime2);
        lesson2.setGroups(groups2);

        Lesson lesson3 = new Lesson();
        lesson3.setProfessor(professor1);
        lesson3.setCourse(course2);
        lesson3.setTime(lessonTime3);
        lesson3.setGroups(groups3);

        Lesson lesson4 = new Lesson();
        lesson4.setProfessor(professor1);
        lesson4.setCourse(course1);
        lesson4.setTime(lessonTime4);
        lesson4.setGroups(groups4);

        Lesson lesson5 = new Lesson();
        lesson5.setProfessor(professor2);
        lesson5.setCourse(course4);
        lesson5.setTime(lessonTime5);
        lesson5.setGroups(groups2);

        Lesson lesson6 = new Lesson();
        lesson6.setProfessor(professor1);
        lesson6.setCourse(course2);
        lesson6.setTime(lessonTime6);
        lesson6.setGroups(groups1);

        Lesson lesson7 = new Lesson();
        lesson7.setProfessor(professor1);
        lesson7.setCourse(course1);
        lesson7.setTime(lessonTime7);
        lesson7.setGroups(groups4);

        Lesson lesson8 = new Lesson();
        lesson8.setProfessor(professor2);
        lesson8.setCourse(course4);
        lesson8.setTime(lessonTime8);
        lesson8.setGroups(groups3);

        lessons.add(lesson1);
        lessons.add(lesson2);
        lessons.add(lesson3);
        lessons.add(lesson4);
        lessons.add(lesson5);
        lessons.add(lesson6);
        lessons.add(lesson7);
        lessons.add(lesson8);
    }

    @Test
    void readProfessorTimetableForADay_ShouldReturnAListOf1CorrectLessonsWhenReceiveAProfessor2AndDate() {
        when(mockedLessonService.readAll()).thenReturn(lessons);

        LocalDateTime date = LocalDateTime.of(2020, 9, 5, 8, 0);
        Timetable professor2TTForADay = timetableService.readProfessorTimetableForADay(2, date);
        List<Lesson> list = professor2TTForADay.getListOfLessons();

        assertEquals(1, list.size());
        assertEquals(group3, list.get(0).getGroups().get(0));
        assertEquals(group4, list.get(0).getGroups().get(1));
        assertEquals(course3, list.get(0).getCourse());
        assertEquals(LocalDateTime.of(2020, 9, 5, 9, 45),
                list.get(0).getTime().getLessonStart());
    }

    @Test
    void readProfessorTimetableForADay_ShouldReturnAListOf2CorrectLessonsWhenReceiveAProfessor1AndDate() {
        when(mockedLessonService.readAll()).thenReturn(lessons);

        LocalDateTime date = LocalDateTime.of(2020, 9, 14, 8, 0);
        Timetable professor1TTForADay = timetableService.readProfessorTimetableForADay(1, date);
        List<Lesson> list = professor1TTForADay.getListOfLessons();

        assertEquals(2, list.size());
        assertEquals(group1, list.get(0).getGroups().get(0));
        assertEquals(group4, list.get(0).getGroups().get(1));
        assertEquals(course2, list.get(0).getCourse());
        assertEquals(LocalDateTime.of(2020, 9, 14, 9, 45),
                list.get(0).getTime().getLessonStart());

        assertEquals(group2, list.get(1).getGroups().get(0));
        assertEquals(group3, list.get(1).getGroups().get(1));
        assertEquals(course1, list.get(1).getCourse());
        assertEquals(LocalDateTime.of(2020, 9, 14, 10, 0),
                list.get(1).getTime().getLessonStart());
    }

    @Test
    void readProfessorTimetableForADay_ShouldReturnAListOf0LessonsWhenReceiveAProfessor1AndDate() {
        when(mockedLessonService.readAll()).thenReturn(lessons);

        LocalDateTime date = LocalDateTime.of(2020, 9, 2, 0, 0);
        Timetable professor1TTForADay = timetableService.readProfessorTimetableForADay(1, date);
        List<Lesson> list = professor1TTForADay.getListOfLessons();

        assertEquals(0, list.size());
    }

    @Test
    void readProfessorTimetableForAMonth_ShouldReturnAListOf3CorrectLessonsWhenReceiveAProfessor1AndDate() {
        when(mockedLessonService.readAll()).thenReturn(lessons);

        LocalDateTime date = LocalDateTime.of(2020, 9, 5, 7, 0);
        Timetable professor1TTForAMonth = timetableService.readProfessorTimetableForAMonth(1, date);
        List<Lesson> list = professor1TTForAMonth.getListOfLessons();

        assertEquals(3, list.size());
        assertEquals(group1, list.get(0).getGroups().get(0));
        assertEquals(group2, list.get(0).getGroups().get(1));
        assertEquals(course1, list.get(0).getCourse());
        assertEquals(LocalDateTime.of(2020, 9, 5, 8, 0),
                list.get(0).getTime().getLessonStart());

        assertEquals(group1, list.get(1).getGroups().get(0));
        assertEquals(group4, list.get(1).getGroups().get(1));
        assertEquals(course2, list.get(1).getCourse());
        assertEquals(LocalDateTime.of(2020, 9, 14, 9, 45),
                list.get(1).getTime().getLessonStart());

        assertEquals(group2, list.get(2).getGroups().get(0));
        assertEquals(group3, list.get(2).getGroups().get(1));
        assertEquals(course1, list.get(2).getCourse());
        assertEquals(LocalDateTime.of(2020, 9, 14, 10, 0),
                list.get(2).getTime().getLessonStart());
    }

    @Test
    void readProfessorTimetableForAMonth_ShouldReturnAListOf2CorrectLessonsWhenReceiveAProfessor1AndDate() {
        when(mockedLessonService.readAll()).thenReturn(lessons);

        LocalDateTime date = LocalDateTime.of(2020, 10, 17, 8, 0);
        Timetable professor1TTForAMonth = timetableService.readProfessorTimetableForAMonth(1, date);
        List<Lesson> list = professor1TTForAMonth.getListOfLessons();

        assertEquals(2, list.size());
        assertEquals(group1, list.get(0).getGroups().get(0));
        assertEquals(group2, list.get(0).getGroups().get(1));
        assertEquals(course2, list.get(0).getCourse());
        assertEquals(LocalDateTime.of(2020, 10, 19, 11, 0),
                list.get(0).getTime().getLessonStart());

        assertEquals(group2, list.get(1).getGroups().get(0));
        assertEquals(group3, list.get(1).getGroups().get(1));
        assertEquals(course1, list.get(1).getCourse());
        assertEquals(LocalDateTime.of(2020, 10, 27, 8, 0),
                list.get(1).getTime().getLessonStart());
    }

    @Test
    void readStudentTimetableForADay_ShouldReturnAListOf1CorrectLessonsWhenReceiveAGroup1AndCourse2AndDate() {
        when(mockedLessonService.readAll()).thenReturn(lessons);

        LocalDateTime date = LocalDateTime.of(2020, 10, 19, 8, 0);
        Timetable studentTTForADay = timetableService.readStudentTimetableForADay(1, 2, date);
        List<Lesson> list = studentTTForADay.getListOfLessons();

        assertEquals(1, list.size());
        assertEquals(professor1, list.get(0).getProfessor());
        assertEquals(LocalDateTime.of(2020, 10, 19, 11, 0),
                list.get(0).getTime().getLessonStart());
    }

    @Test
    void readStudentTimetableForADay_ShouldReturnAListOf0LessonWhenReceiveAGroup1AndCourse1AndDate() {
        when(mockedLessonService.readAll()).thenReturn(lessons);

        LocalDateTime date = LocalDateTime.of(2020, 9, 2, 0, 0);
        Timetable studentTTForADay = timetableService.readStudentTimetableForADay(1, 2, date);
        List<Lesson> list = studentTTForADay.getListOfLessons();

        assertEquals(0, list.size());
    }

    @Test
    void readStudentTimetableForAMonth_ShouldReturnAListOf2CorrectLessonsWhenReceiveAGroup2AndCourse1AndDate() {
        when(mockedLessonService.readAll()).thenReturn(lessons);

        LocalDateTime date = LocalDateTime.of(2020, 9, 5, 7, 0);
        Timetable student1TTForAMonth = timetableService.readStudentTimetableForAMonth(2, 1, date);
        List<Lesson> list = student1TTForAMonth.getListOfLessons();

        assertEquals(2, list.size());
        assertEquals(professor1, list.get(0).getProfessor());
        assertEquals(LocalDateTime.of(2020, 9, 5, 8, 0),
                list.get(0).getTime().getLessonStart());

        assertEquals(professor1, list.get(1).getProfessor());
        assertEquals(LocalDateTime.of(2020, 9, 14, 10, 0),
                list.get(1).getTime().getLessonStart());
    }
}