package com.foxminded.university.dao.entities;

import lombok.Data;

import java.util.List;

@Data
public class Lesson {
    private Integer lessonId;
    private Professor professor;
    private Course course;
    private ClassRoom classRoom;
    private LessonTime time;
    private List<Group> groups;
}
