package com.foxminded.university;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Timetable {

    private List<Lesson> listOfLessons;

    public Timetable() {
        listOfLessons = new ArrayList<>();
    }

    public void addLesson(Lesson  lesson) {
        listOfLessons.add(lesson);
    }

    public void addLessons(Lesson ... lesson) {
        listOfLessons.addAll(Arrays.asList(lesson));
    }

    public void removeLesson(Lesson lesson) {
        listOfLessons.remove(lesson);
    }

    public List<Lesson> getListOfLessons() {
        return listOfLessons;
    }
}
