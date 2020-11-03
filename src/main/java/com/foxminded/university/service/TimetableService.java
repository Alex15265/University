package com.foxminded.university.service;

import com.foxminded.university.dao.entities.Group;
import com.foxminded.university.dao.entities.Lesson;
import com.foxminded.university.dao.entities.Timetable;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class TimetableService {
    private final LessonService lessonService;

    public Timetable findByProfessor(Integer professorId, LocalDateTime start, LocalDateTime end) {
        Timetable professorTimetable = new Timetable();
        List<Lesson> listOfLessons = new ArrayList<>();
        for(Lesson lesson: lessonService.readAll()) {
            if(lesson.getProfessor().getProfessorId().equals(professorId) && lesson.getTime().getLessonStart().isAfter(start)
                    && lesson.getTime().getLessonStart().isBefore(end)) {
                listOfLessons.add(lesson);
            }
        }
        professorTimetable.setListOfLessons(listOfLessons);
        return professorTimetable;
    }

    public Timetable findByGroup(Integer groupId, LocalDateTime start, LocalDateTime end) {
        Timetable studentTimetable = new Timetable();
        List<Lesson> listOfLessons = new ArrayList<>();
        for(Lesson lesson: lessonService.readAll()) {
            if(lesson.getTime().getLessonStart().isAfter(start)
                    && lesson.getTime().getLessonStart().isBefore(end)) {
                for(Group group: lesson.getGroups()) {
                    if(group.getGroupId().equals(groupId)) {
                        listOfLessons.add(lesson);
                    }
                }
            }
        }
        studentTimetable.setListOfLessons(listOfLessons);
        return studentTimetable;
    }
}
