package com.foxminded.university.dao.entities;

import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Data
@Component
@Scope("prototype")
public class LessonTime {
    private Integer timeId;
    private LocalDateTime lessonStart;
    private LocalDateTime lessonEnd;
}
