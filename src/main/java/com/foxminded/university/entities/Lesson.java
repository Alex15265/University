package com.foxminded.university.entities;

import lombok.Data;

import java.util.List;

@Data
public class Lesson {
    private Professor professor;
    private Course course;
    private ClassRoom classRoom;
    private LessonTime time;
    private List<Group> groups;
}
