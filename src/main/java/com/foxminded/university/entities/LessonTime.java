package com.foxminded.university.entities;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class LessonTime {
    private LocalDateTime lessonStart;
    private LocalDateTime lessonEnd;
}
