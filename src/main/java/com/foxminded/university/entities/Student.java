package com.foxminded.university.entities;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
@Entity
@Table(name = "students")
public class Student {
    @Id
    @Column(name = "student_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer studentId;
    @Column(name = "first_name")
    @NotBlank(message = "This field cannot be empty")
    private String firstName;
    @Column(name = "last_name")
    @NotBlank(message = "This field cannot be empty")
    private String lastName;
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="group_id")
    private Group group;
    @ManyToMany(fetch=FetchType.LAZY)
    @JoinTable(
            name = "students_courses",
            joinColumns = { @JoinColumn(name = "student_id") },
            inverseJoinColumns = { @JoinColumn(name = "course_id") }
    )
    private List<Course> courses;
}
