package com.foxminded.university.dao.entities;

import lombok.Data;

import java.util.List;

@Data
public class Professor {
    private Integer professorId;
    private String firstName;
    private String lastName;
    private List<Course> courses;
}
