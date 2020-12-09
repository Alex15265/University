package com.foxminded.university.entities;

import lombok.Data;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
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
    @NotNull(message = "This field cannot be empty")
    private LocalDateTime lessonStart;
    @Column(name = "lesson_end")
    @NotNull(message = "This field cannot be empty")
    private LocalDateTime lessonEnd;
}
