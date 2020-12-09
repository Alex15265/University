package com.foxminded.university.entities;

import lombok.Data;
import java.util.List;

@Data
public class University {
    private Integer id;
    private List<Professor> professors;
    private List<Student> students;
    private List<Course> courses;
    private List<Group> groups;
    private List<ClassRoom> classRooms;
    private Timetable timetable;
}
