package com.foxminded.university.entities;

import lombok.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Data
public class Timetable {
    private List<Lesson> listOfLessons = new ArrayList<>();

    public void addLesson(Lesson  lesson) {
        listOfLessons.add(lesson);
    }

    public void addLessons(Lesson ... lesson) {
        listOfLessons.addAll(Arrays.asList(lesson));
    }

    public void removeLesson(Lesson lesson) {
        listOfLessons.remove(lesson);
    }
}
