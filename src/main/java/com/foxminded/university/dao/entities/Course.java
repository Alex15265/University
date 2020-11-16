package com.foxminded.university.dao.entities;

import lombok.Data;

import java.util.List;

@Data
public class Course {
    private Integer courseId;
    private String courseName;
    private String description;
    private Integer professorId;
    private List<Student> students;
}
