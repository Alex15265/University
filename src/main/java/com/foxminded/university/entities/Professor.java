package com.foxminded.university.entities;

import lombok.Data;

import java.util.List;

@Data
public class Professor {
    private Integer id;
    private String name;
    private String lastName;
    private List<Course> courses;
}
