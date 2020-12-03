package com.foxminded.university.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "courses")
public class Course {
    @Id
    @Column(name = "course_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer courseId;
    @Column(name = "course_name")
    private String courseName;
    @Column(name = "course_description")
    private String description;
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="professor_id")
    private Professor professor;
    @ManyToMany(fetch=FetchType.LAZY)
    @JoinTable(
            name = "students_courses",
            joinColumns = { @JoinColumn(name = "course_id") },
            inverseJoinColumns = { @JoinColumn(name = "student_id") }
    )
    private List<Student> students;
}
