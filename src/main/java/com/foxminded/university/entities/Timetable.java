package com.foxminded.university.entities;

import lombok.Data;

import java.util.List;

@Data
public class Timetable {
    private Integer id;
    private List<Lesson> listOfLessons;
}
