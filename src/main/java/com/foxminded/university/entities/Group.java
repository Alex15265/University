package com.foxminded.university.entities;

import lombok.Data;

import java.util.List;

@Data
public class Group {
    private Integer id;
    private String groupName;
    private List<Student> students;
}
