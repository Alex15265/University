package com.foxminded.university.dto.professor;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ProfessorDTORequest {
    @NotBlank(message = "This field cannot be empty")
    @ApiModelProperty(value = "The professor's first name")
    private String firstName;
    @NotBlank(message = "This field cannot be empty")
    @ApiModelProperty(value = "The professor's last name")
    private String lastName;
}
