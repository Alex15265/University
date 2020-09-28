package com.foxminded.university.entities;

import lombok.Data;

import java.util.List;

@Data
public class Course {
    private Integer id;
    private String courseName;
    private String description;
    private List<Student> students;
}
