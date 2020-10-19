package com.foxminded.university.dao.entities;

import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component
@Scope("prototype")
public class Group {
    private Integer groupId;
    private String groupName;
    private List<Student> students;
}
