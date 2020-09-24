package com.foxminded.university.init;

import com.foxminded.university.entities.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TimetableGenerator {
    private University university = new University();

    public Timetable readProfessorTimetableForADay(Professor professor, LocalDateTime date) {
        Timetable professorTimetableForADay = new Timetable();
        for(Lesson lesson: university.getTimetable().getListOfLessons()) {
            if(lesson.getProfessor().equals(professor) && lesson.getTime().getLessonStart().isAfter(date)
                    && lesson.getTime().getLessonStart().isBefore(date.plusHours(24))) {
                professorTimetableForADay.addLesson(lesson);
            }
        }
        return professorTimetableForADay;
    }

    public Timetable readProfessorTimetableForAMonth(Professor professor, LocalDateTime date) {
        Timetable professorTimetableForAMonth = new Timetable();
        for(Lesson lesson: university.getTimetable().getListOfLessons()) {
            if(lesson.getProfessor().equals(professor) && lesson.getTime().getLessonStart().isAfter(date)
                    && lesson.getTime().getLessonStart().isBefore(date.plusMonths(1))) {
                professorTimetableForAMonth.addLesson(lesson);
            }
        }
        return professorTimetableForAMonth;
    }

    public Timetable readStudentTimetableForADay(Group group, Course course, LocalDateTime date) {
        Timetable studentTimetableForADay = new Timetable();
        for(Lesson lesson: university.getTimetable().getListOfLessons()) {
            if(lesson.getCourse().equals(course) && lesson.getTime().getLessonStart().isAfter(date)
                    && lesson.getTime().getLessonStart().isBefore(date.plusHours(24))) {
                for(Group g: lesson.getGroups()) {
                    if(g.equals(group)) {
                        studentTimetableForADay.addLesson(lesson);
                    }
                }
            }
        }
        return studentTimetableForADay;
    }

    public Timetable readStudentTimetableForAMonth(Group group, Course course, LocalDateTime date) {
        Timetable readStudentTimetableForAMonth = new Timetable();
        for(Lesson lesson: university.getTimetable().getListOfLessons()) {
            if(lesson.getCourse().equals(course) && lesson.getTime().getLessonStart().isAfter(date)
                    && lesson.getTime().getLessonStart().isBefore(date.plusMonths(1))) {
                for(Group g: lesson.getGroups()) {
                    if(g.equals(group)) {
                        readStudentTimetableForAMonth.addLesson(lesson);
                    }
                }
            }
        }
        return readStudentTimetableForAMonth;
    }
}
