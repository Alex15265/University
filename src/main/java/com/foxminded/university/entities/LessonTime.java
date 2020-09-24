package com.foxminded.university.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class LessonTime {
    @NonNull
    private LocalDateTime lessonStart;
    private LocalDateTime lessonEnd;
}
