package com.foxminded.university.entities;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@Entity
@Table(name = "groups")
public class Group {
    @Id
    @Column(name = "group_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer groupId;
    @Column(name = "group_name")
    @NotEmpty(message = "This field cannot be empty")
    private String groupName;
    @OneToMany(mappedBy = "group", fetch = FetchType.LAZY, cascade= {CascadeType.REMOVE})
    private List<Student> students;

    @Override
    public String toString() {
        return groupName;
    }
}
