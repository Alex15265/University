package com.foxminded.university.entities;

import lombok.Data;
import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "lessons")
public class Lesson {
    @Id
    @Column(name = "lesson_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer lessonId;
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="professor_id")
    private Professor professor;
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="course_id")
    private Course course;
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="room_id")
    private ClassRoom classRoom;
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="time_id")
    private LessonTime time;
    @ManyToMany(fetch=FetchType.EAGER)
    @JoinTable(
            name = "groups_lessons",
            joinColumns = { @JoinColumn(name = "lesson_id") },
            inverseJoinColumns = { @JoinColumn(name = "group_id") }
    )
    private List<Group> groups;
}
