package com.foxminded.university.dto.courses;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@Data
public class CourseDTORequest {
    @NotBlank(message = "This field cannot be empty")
    private String courseName;
    @Size(min=10, max = 400, message = "Field size must be between 10 and 400")
    private String description;
    @Positive(message = "This field must be Positive Number")
    private Integer professorId;
}
