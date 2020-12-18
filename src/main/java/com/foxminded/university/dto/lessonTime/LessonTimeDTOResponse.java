package com.foxminded.university.dto.lessonTime;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class LessonTimeDTOResponse {
    private Integer timeId;
    private LocalDateTime lessonStart;
    private LocalDateTime lessonEnd;
}
