package com.foxminded.university.dao.entities;

import lombok.Data;

@Data
public class Student {
    private Integer studentId;
    private String firstName;
    private String lastName;
    private Integer groupId;
}
