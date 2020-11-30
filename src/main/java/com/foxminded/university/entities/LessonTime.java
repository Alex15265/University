package com.foxminded.university.entities;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "times")
public class LessonTime {
    @Id
    @Column(name = "time_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer timeId;
    @Column(name = "lesson_start")
    private LocalDateTime lessonStart;
    @Column(name = "lesson_end")
    private LocalDateTime lessonEnd;
}
