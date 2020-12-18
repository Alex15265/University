package com.foxminded.university.dto.lesson;

import lombok.Data;

import javax.validation.constraints.Positive;

@Data
public class LessonDTORequest {
    @Positive(message = "This field must be Positive Number")
    Integer professorId;
    @Positive(message = "This field must be Positive Number")
    Integer courseId;
    @Positive(message = "This field must be Positive Number")
    Integer roomId;
    @Positive(message = "This field must be Positive Number")
    Integer timeId;
}
