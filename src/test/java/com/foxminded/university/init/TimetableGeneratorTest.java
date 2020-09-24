package com.foxminded.university.init;

import com.foxminded.university.entities.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

class TimetableGeneratorTest {
    private static TimetableGenerator generator;
    private static Professor professor1;
    private static Professor professor2;
    private static Group group1;
    private static Group group2;
    private static Group group3;
    private static Group group4;
    private static Course course1;
    private static Course course2;
    private static Course course3;
    private static Course course4;
    private static ClassRoom classRoom1;
    private static ClassRoom classRoom2;

    @BeforeAll
    static void initTimetable() {
        generator = new TimetableGenerator();
        course1 = new Course("History", "HistoryDescription");
        course2 = new Course("English", "EnglishDescription");
        course3 = new Course("Robotics", "RoboticsDescription");
        course4 = new Course("Russian", "RussianDescription");
        professor1 = new Professor(1, "John", "Smith");
        professor1.addCourse(course1);
        professor1.addCourse(course2);
        professor2 = new Professor(2, "Lisa", "Liberman");
        professor2.addCourse(course3);
        professor2.addCourse(course4);
        group1 = new Group("aa-01");
        group2 = new Group("aa-02");
        group3 = new Group("aa-03");
        group4 = new Group("aa-04");
        LessonTime lessonTime1 = new LessonTime(LocalDateTime.of(2020, 9, 1, 8, 0));
        LessonTime lessonTime2 = new LessonTime(LocalDateTime.of(2020, 9, 5, 8, 0));
        LessonTime lessonTime3 = new LessonTime(LocalDateTime.of(2020, 9, 5, 9, 45));
        LessonTime lessonTime4 = new LessonTime(LocalDateTime.of(2020, 9, 14, 8, 0));
        LessonTime lessonTime5 = new LessonTime(LocalDateTime.of(2020, 9, 14, 9, 45));
        LessonTime lessonTime6 = new LessonTime(LocalDateTime.of(2020, 9, 14, 10, 0));
        LessonTime lessonTime7 = new LessonTime(LocalDateTime.of(2020, 9, 14, 11, 30));
        LessonTime lessonTime8 = new LessonTime(LocalDateTime.of(2020, 9, 28, 8, 0));
        LessonTime lessonTime9 = new LessonTime(LocalDateTime.of(2020, 9, 28, 9, 45));
        LessonTime lessonTime10 = new LessonTime(LocalDateTime.of(2020, 10, 2, 8, 0));
        LessonTime lessonTime11 = new LessonTime(LocalDateTime.of(2020, 10, 2, 9, 45));
        LessonTime lessonTime12 = new LessonTime(LocalDateTime.of(2020, 10, 19, 8, 0));
        LessonTime lessonTime13 = new LessonTime(LocalDateTime.of(2020, 10, 19, 9, 45));
        LessonTime lessonTime14 = new LessonTime(LocalDateTime.of(2020, 10, 19, 11, 0));
        LessonTime lessonTime15 = new LessonTime(LocalDateTime.of(2020, 10, 27, 8, 0));
        LessonTime lessonTime16 = new LessonTime(LocalDateTime.of(2020, 10, 27, 8, 0));
        LessonTime lessonTime17 = new LessonTime(LocalDateTime.of(2020, 11, 3, 8, 0));
        LessonTime lessonTime18 = new LessonTime(LocalDateTime.of(2020, 11, 11, 8, 0));
        LessonTime lessonTime19 = new LessonTime(LocalDateTime.of(2020, 11, 11, 9, 45));
        LessonTime lessonTime20 = new LessonTime(LocalDateTime.of(2020, 11, 22, 8, 0));
        classRoom1 = new ClassRoom(1);
        classRoom2 = new ClassRoom(2);
        Lesson lesson1 = new Lesson(professor1, course1, classRoom1, lessonTime1);
        lesson1.addGroup(group1);
        Lesson lesson2 = new Lesson(professor2, course3, classRoom2, lessonTime2);
        lesson2.addGroups(group3, group4);
        Lesson lesson3 = new Lesson(professor1, course2, classRoom1, lessonTime3);
        lesson3.addGroup(group2);
        Lesson lesson4 = new Lesson(professor1, course1, classRoom1, lessonTime4);
        lesson4.addGroups(group2, group3, group4);
        Lesson lesson5 = new Lesson(professor2, course4, classRoom2, lessonTime5);
        lesson5.addGroup(group1);
        Lesson lesson6 = new Lesson(professor1, course2, classRoom1, lessonTime6);
        lesson6.addGroups(group1, group2);
        Lesson lesson7 = new Lesson(professor2, course3, classRoom2, lessonTime7);
        lesson7.addGroups(group3, group4);
        Lesson lesson8 = new Lesson(professor1, course1, classRoom1, lessonTime8);
        lesson8.addGroups(group1, group2, group3);
        Lesson lesson9 = new Lesson(professor2, course4, classRoom2, lessonTime9);
        lesson9.addGroup(group4);
        Lesson lesson10 = new Lesson(professor1, course2, classRoom1, lessonTime10);
        lesson10.addGroup(group1);
        Lesson lesson11 = new Lesson(professor2, course3, classRoom2, lessonTime11);
        lesson11.addGroups(group2, group3, group4);
        Lesson lesson12 = new Lesson(professor2, course4, classRoom2, lessonTime12);
        lesson12.addGroups(group1, group3);
        Lesson lesson13 = new Lesson(professor1, course1, classRoom1, lessonTime13);
        lesson13.addGroups(group2, group4);
        Lesson lesson14 = new Lesson(professor1, course1, classRoom1, lessonTime14);
        lesson14.addGroups(group1, group2, group3, group4);
        Lesson lesson15 = new Lesson(professor2, course3, classRoom2,lessonTime15);
        lesson15.addGroups(group2, group3);
        Lesson lesson16 = new Lesson(professor2, course3, classRoom2,lessonTime16);
        lesson16.addGroups(group1, group4);
        Lesson lesson17 = new Lesson(professor1, course2, classRoom1, lessonTime17);
        lesson17.addGroups(group1, group2, group3, group4);
        Lesson lesson18 = new Lesson(professor2, course4, classRoom2, lessonTime18);
        lesson18.addGroups(group3, group4);
        Lesson lesson19 = new Lesson(professor1, course1, classRoom1, lessonTime19);
        lesson19.addGroups(group1, group2);
        Lesson lesson20 = new Lesson(professor2, course4, classRoom2, lessonTime20);
        lesson20.addGroups(group1, group2, group3, group4);
        generator.getUniversity().getTimetable().addLessons(lesson1, lesson2, lesson3, lesson4, lesson5, lesson6, lesson7, lesson8, lesson9, lesson10,
                lesson11, lesson12, lesson13, lesson14, lesson15, lesson16, lesson17, lesson18, lesson19, lesson20);
    }

    @Test
    void readProfessorTimetableForADay_ShouldReturnAListOf1CorrectLessonsWhenReceiveAProfessor1AndDate() {
        LocalDateTime date = LocalDateTime.of(2020, 10, 2, 0, 0);
        Timetable professor1TTForADay = generator.readProfessorTimetableForADay(professor1, date);
        List<Lesson> list = professor1TTForADay.getListOfLessons();
        Assertions.assertEquals(1, list.size());
        Assertions.assertEquals(group1, list.get(0).getGroups().get(0));
        Assertions.assertEquals(course2, list.get(0).getCourse());
        Assertions.assertEquals(classRoom1, list.get(0).getClassRoom());
        Assertions.assertEquals(LocalDateTime.of(2020, 10, 2, 8, 0),
                list.get(0).getTime().getLessonStart());
    }

    @Test
    void readProfessorTimetableForADay_ShouldReturnAListOf2CorrectLessonsWhenReceiveAProfessor2AndDate() {
        LocalDateTime date = LocalDateTime.of(2020, 9, 14, 0, 0);
        Timetable professor2TTForADay = generator.readProfessorTimetableForADay(professor2, date);
        List<Lesson> list = professor2TTForADay.getListOfLessons();
        Assertions.assertEquals(2, list.size());
        Assertions.assertEquals(group1, list.get(0).getGroups().get(0));
        Assertions.assertEquals(course4, list.get(0).getCourse());
        Assertions.assertEquals(classRoom2, list.get(0).getClassRoom());
        Assertions.assertEquals(LocalDateTime.of(2020, 9, 14, 9, 45),
                list.get(0).getTime().getLessonStart());

        Assertions.assertEquals(group3, list.get(1).getGroups().get(0));
        Assertions.assertEquals(group4, list.get(1).getGroups().get(1));
        Assertions.assertEquals(course3, list.get(1).getCourse());
        Assertions.assertEquals(classRoom2, list.get(0).getClassRoom());
        Assertions.assertEquals(LocalDateTime.of(2020, 9, 14, 11, 30),
                list.get(1).getTime().getLessonStart());
    }

    @Test
    void readProfessorTimetableForADay_ShouldReturnAListOf0LessonsWhenReceiveAProfessor1AndDate() {
        LocalDateTime date = LocalDateTime.of(2020, 9, 2, 0, 0);
        Timetable professor1TTForADay = generator.readProfessorTimetableForADay(professor1, date);
        List<Lesson> list = professor1TTForADay.getListOfLessons();
        Assertions.assertEquals(0, list.size());
    }

    @Test
    void readProfessorTimetableForAMonth_ShouldReturnAListOf3CorrectLessonsWhenReceiveAProfessor1AndDate() {
        LocalDateTime date = LocalDateTime.of(2020, 10, 2, 0, 0);
        Timetable professor1TTForAMonth = generator.readProfessorTimetableForAMonth(professor1, date);
        List<Lesson> list = professor1TTForAMonth.getListOfLessons();
        Assertions.assertEquals(3, list.size());
        Assertions.assertEquals(group1, list.get(0).getGroups().get(0));
        Assertions.assertEquals(course2, list.get(0).getCourse());
        Assertions.assertEquals(classRoom1, list.get(0).getClassRoom());
        Assertions.assertEquals(LocalDateTime.of(2020, 10, 2, 8, 0),
                list.get(0).getTime().getLessonStart());

        Assertions.assertEquals(group2, list.get(1).getGroups().get(0));
        Assertions.assertEquals(group4, list.get(1).getGroups().get(1));
        Assertions.assertEquals(course1, list.get(1).getCourse());
        Assertions.assertEquals(classRoom1, list.get(1).getClassRoom());
        Assertions.assertEquals(LocalDateTime.of(2020, 10, 19, 9, 45),
                list.get(1).getTime().getLessonStart());

        Assertions.assertEquals(group1, list.get(2).getGroups().get(0));
        Assertions.assertEquals(group2, list.get(2).getGroups().get(1));
        Assertions.assertEquals(group3, list.get(2).getGroups().get(2));
        Assertions.assertEquals(group4, list.get(2).getGroups().get(3));
        Assertions.assertEquals(course1, list.get(2).getCourse());
        Assertions.assertEquals(classRoom1, list.get(2).getClassRoom());
        Assertions.assertEquals(LocalDateTime.of(2020, 10, 19, 11, 0),
                list.get(2).getTime().getLessonStart());
    }

    @Test
    void readProfessorTimetableForAMonth_ShouldReturnAListOf2CorrectLessonsWhenReceiveAProfessor2AndDate() {
        LocalDateTime date = LocalDateTime.of(2020, 11, 1, 0, 0);
        Timetable professor1TTForAMonth = generator.readProfessorTimetableForAMonth(professor2, date);
        List<Lesson> list = professor1TTForAMonth.getListOfLessons();
        Assertions.assertEquals(2, list.size());
        Assertions.assertEquals(group3, list.get(0).getGroups().get(0));
        Assertions.assertEquals(group4, list.get(0).getGroups().get(1));
        Assertions.assertEquals(course4, list.get(0).getCourse());
        Assertions.assertEquals(classRoom2, list.get(0).getClassRoom());
        Assertions.assertEquals(LocalDateTime.of(2020, 11, 11, 8, 0),
                list.get(0).getTime().getLessonStart());

        Assertions.assertEquals(group1, list.get(1).getGroups().get(0));
        Assertions.assertEquals(group2, list.get(1).getGroups().get(1));
        Assertions.assertEquals(group3, list.get(1).getGroups().get(2));
        Assertions.assertEquals(group4, list.get(1).getGroups().get(3));
        Assertions.assertEquals(course4, list.get(1).getCourse());
        Assertions.assertEquals(classRoom2, list.get(1).getClassRoom());
        Assertions.assertEquals(LocalDateTime.of(2020, 11, 22, 8, 0),
                list.get(1).getTime().getLessonStart());
    }

    @Test
    void readStudentTimetableForADay_ShouldReturnAListOf2CorrectLessonsWhenReceiveAGroup2AndCourse1AndDate() {
        LocalDateTime date = LocalDateTime.of(2020, 10, 19, 0, 0);
        Timetable studentTTForADay = generator.readStudentTimetableForADay(group2, course1, date);
        List<Lesson> list = studentTTForADay.getListOfLessons();
        Assertions.assertEquals(2, list.size());
        Assertions.assertEquals(professor1, list.get(0).getProfessor());
        Assertions.assertEquals(classRoom1, list.get(0).getClassRoom());
        Assertions.assertEquals(LocalDateTime.of(2020, 10, 19, 9, 45),
                list.get(0).getTime().getLessonStart());

        Assertions.assertEquals(professor1, list.get(1).getProfessor());
        Assertions.assertEquals(classRoom1, list.get(1).getClassRoom());
        Assertions.assertEquals(LocalDateTime.of(2020, 10, 19, 11, 0),
                list.get(1).getTime().getLessonStart());
    }

    @Test
    void readStudentTimetableForADay_ShouldReturnAListOf1CorrectLessonWhenReceiveAGroup1AndCourse1AndDate() {
        LocalDateTime date = LocalDateTime.of(2020, 9, 1, 0, 0);
        Timetable studentTTForADay = generator.readStudentTimetableForADay(group1, course1, date);
        List<Lesson> list = studentTTForADay.getListOfLessons();
        Assertions.assertEquals(1, list.size());
        Assertions.assertEquals(professor1, list.get(0).getProfessor());
        Assertions.assertEquals(classRoom1, list.get(0).getClassRoom());
        Assertions.assertEquals(LocalDateTime.of(2020, 9, 1, 8, 0),
                list.get(0).getTime().getLessonStart());
    }

    @Test
    void readStudentTimetableForADay_ShouldReturnAListOf0LessonWhenReceiveAGroup1AndCourse1AndDate() {
        LocalDateTime date = LocalDateTime.of(2020, 9, 2, 0, 0);
        Timetable studentTTForADay = generator.readStudentTimetableForADay(group1, course1, date);
        List<Lesson> list = studentTTForADay.getListOfLessons();
        Assertions.assertEquals(0, list.size());
    }

    @Test
    void readStudentTimetableForAMonth_ShouldReturnAListOf2CorrectLessonsWhenReceiveAGroup2AndCourse1AndDate() {
        LocalDateTime date = LocalDateTime.of(2020, 9, 1, 0, 0);
        Timetable student1TTForAMonth = generator.readStudentTimetableForAMonth(group2, course1, date);
        List<Lesson> list = student1TTForAMonth.getListOfLessons();
        Assertions.assertEquals(2, list.size());
        Assertions.assertEquals(professor1, list.get(0).getProfessor());
        Assertions.assertEquals(classRoom1, list.get(0).getClassRoom());
        Assertions.assertEquals(LocalDateTime.of(2020, 9, 14, 8, 0),
                list.get(0).getTime().getLessonStart());

        Assertions.assertEquals(professor1, list.get(1).getProfessor());
        Assertions.assertEquals(classRoom1, list.get(1).getClassRoom());
        Assertions.assertEquals(LocalDateTime.of(2020, 9, 28, 8, 0),
                list.get(1).getTime().getLessonStart());
    }

    @Test
    void readStudentTimetableForAMonth_ShouldReturnAListOf1CorrectLessonsWhenReceiveAGroup3AndCourse4AndDate() {
        LocalDateTime date = LocalDateTime.of(2020, 9, 29, 0, 0);
        Timetable student1TTForAMonth = generator.readStudentTimetableForAMonth(group3, course4, date);
        List<Lesson> list = student1TTForAMonth.getListOfLessons();
        Assertions.assertEquals(1, list.size());
        Assertions.assertEquals(professor2, list.get(0).getProfessor());
        Assertions.assertEquals(classRoom2, list.get(0).getClassRoom());
        Assertions.assertEquals(LocalDateTime.of(2020, 10, 19, 8, 0),
                list.get(0).getTime().getLessonStart());

    }
}