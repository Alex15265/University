package com.foxminded.university.dto.lessonTime;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
public class LessonTimeDTORequest {
    @NotBlank(message = "This field cannot be empty")
    @Pattern(message = "Invalid data format", regexp = "[0-9]{2}.[0-9]{2}.[0-9]{2} [0-9]{1,2}:[0-9]{2}")
    private String lessonStart;
    @NotBlank(message = "This field cannot be empty")
    @Pattern(message = "Invalid data format", regexp = "[0-9]{2}.[0-9]{2}.[0-9]{2} [0-9]{1,2}:[0-9]{2}")
    private String lessonEnd;
}
