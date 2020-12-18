package com.foxminded.university.dto.professor;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ProfessorDTORequest {
    @NotBlank(message = "This field cannot be empty")
    private String firstName;
    @NotBlank(message = "This field cannot be empty")
    private String lastName;
}
