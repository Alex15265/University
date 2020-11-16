package com.foxminded.university.dao.entities;

import lombok.Data;

import java.util.List;

@Data
public class Group {
    private Integer groupId;
    private String groupName;
    private List<Student> students;

    @Override
    public String toString() {
        return groupName;
    }
}
