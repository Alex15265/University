package com.foxminded.university.dao.entities;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class LessonTime {
    private Integer timeId;
    private LocalDateTime lessonStart;
    private LocalDateTime lessonEnd;
}
