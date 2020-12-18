package com.foxminded.university.dto.student;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

@Data
public class StudentDTORequest {
    @NotBlank(message = "This field cannot be empty")
    private String firstName;
    @NotBlank(message = "This field cannot be empty")
    private String lastName;
    @Positive(message = "This field must be Positive Number")
    private Integer groupId;
}
