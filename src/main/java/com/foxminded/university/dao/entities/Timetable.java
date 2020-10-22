package com.foxminded.university.dao.entities;

import lombok.Data;

import java.util.List;

@Data
public class Timetable {
    private List<Lesson> listOfLessons;
}
