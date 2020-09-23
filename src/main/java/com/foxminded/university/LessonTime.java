package com.foxminded.university;

import java.time.LocalDateTime;

public class LessonTime {
    private LocalDateTime lessonStart;
    private LocalDateTime lessonEnd;

    public LessonTime() {
    }

    public LessonTime(LocalDateTime lessonStart) {
        this.lessonStart = lessonStart;
        this.lessonEnd = lessonStart.plusMinutes(90);
    }

    public LocalDateTime getLessonStart() {
        return lessonStart;
    }

    public void setLessonStart(LocalDateTime lessonStart) {
        this.lessonStart = lessonStart;
    }

    public LocalDateTime getLessonEnd() {
        return lessonEnd;
    }

    public void setLessonEnd(LocalDateTime lessonEnd) {
        this.lessonEnd = lessonEnd;
    }
}
