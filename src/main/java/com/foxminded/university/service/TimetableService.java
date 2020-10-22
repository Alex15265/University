package com.foxminded.university.service;

import com.foxminded.university.dao.entities.Group;
import com.foxminded.university.dao.entities.Lesson;
import com.foxminded.university.dao.entities.Timetable;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TimetableService {
    private final LessonService lessonService;

    public TimetableService(LessonService lessonService) {
        this.lessonService =lessonService;
    }

    public Timetable readProfessorTimetableForADay(Integer professorId, LocalDateTime date) {
        Timetable professorTimetableForADay = new Timetable();
        List<Lesson> listOfLessons = new ArrayList<>();
        for(Lesson lesson: lessonService.readAll()) {
            if(lesson.getProfessor().getProfessorId().equals(professorId) && lesson.getTime().getLessonStart().isAfter(date)
                    && lesson.getTime().getLessonStart().isBefore(date.plusHours(24))) {
                listOfLessons.add(lesson);
            }
        }
        professorTimetableForADay.setListOfLessons(listOfLessons);
        return professorTimetableForADay;
    }

    public Timetable readProfessorTimetableForAMonth(Integer professorId, LocalDateTime date) {
        Timetable professorTimetableForAMonth = new Timetable();
        List<Lesson> listOfLessons = new ArrayList<>();
        for(Lesson lesson: lessonService.readAll()) {
            if(lesson.getProfessor().getProfessorId().equals(professorId) && lesson.getTime().getLessonStart().isAfter(date)
                    && lesson.getTime().getLessonStart().isBefore(date.plusMonths(1))) {
                listOfLessons.add(lesson);
            }
        }
        professorTimetableForAMonth.setListOfLessons(listOfLessons);
        return professorTimetableForAMonth;
    }

    public Timetable readStudentTimetableForADay(Integer groupId, Integer courseId, LocalDateTime date) {
        Timetable studentTimetableForADay = new Timetable();
        List<Lesson> listOfLessons = new ArrayList<>();
        for(Lesson lesson: lessonService.readAll()) {
            if(lesson.getCourse().getCourseId().equals(courseId) && lesson.getTime().getLessonStart().isAfter(date)
                    && lesson.getTime().getLessonStart().isBefore(date.plusHours(24))) {
                for(Group group: lesson.getGroups()) {
                    if(group.getGroupId().equals(groupId)) {
                        listOfLessons.add(lesson);
                    }
                }
            }
        }
        studentTimetableForADay.setListOfLessons(listOfLessons);
        return studentTimetableForADay;
    }

    public Timetable readStudentTimetableForAMonth(Integer groupId, Integer courseId, LocalDateTime date) {
        Timetable studentTimetableForAMonth = new Timetable();
        List<Lesson> listOfLessons = new ArrayList<>();
        for(Lesson lesson: lessonService.readAll()) {
            if(lesson.getCourse().getCourseId().equals(courseId) && lesson.getTime().getLessonStart().isAfter(date)
                    && lesson.getTime().getLessonStart().isBefore(date.plusMonths(1))) {
                for(Group group: lesson.getGroups()) {
                    if(group.getGroupId().equals(groupId)) {
                        listOfLessons.add(lesson);
                    }
                }
            }
        }
        studentTimetableForAMonth.setListOfLessons(listOfLessons);
        return studentTimetableForAMonth;
    }
}
