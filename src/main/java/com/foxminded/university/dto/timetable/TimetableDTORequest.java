package com.foxminded.university.dto.timetable;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;

@Data
public class TimetableDTORequest {
    @Positive(message = "This field must be Positive Number")
    private Integer professorId;
    @Positive(message = "This field must be Positive Number")
    private Integer groupId;
    @NotBlank(message = "This field cannot be empty")
    @Pattern(message = "Invalid data format", regexp = "[0-9]{2}.[0-9]{2}.[0-9]{2} [0-9]{1,2}:[0-9]{2}")
    private String startTime;
    @NotBlank(message = "This field cannot be empty")
    @Pattern(message = "Invalid data format", regexp = "[0-9]{2}.[0-9]{2}.[0-9]{2} [0-9]{1,2}:[0-9]{2}")
    private String endTime;
}
