package com.foxminded.university.dao.entities;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component
@Scope("prototype")
public class Course {
    private Integer courseId;
    private String courseName;
    private String description;
    @Autowired
    private List<Student> students;
}
