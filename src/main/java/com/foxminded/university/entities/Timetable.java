package com.foxminded.university.entities;

import lombok.Data;
import java.util.List;

@Data
public class Timetable {
    private List<Lesson> listOfLessons;
}
