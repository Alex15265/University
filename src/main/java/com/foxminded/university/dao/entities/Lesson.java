package com.foxminded.university.dao.entities;

import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component
@Scope("prototype")
public class Lesson {
    private Integer lessonId;
    private Professor professor;
    private Course course;
    private ClassRoom classRoom;
    private LessonTime time;
    private List<Group> groups;
}
