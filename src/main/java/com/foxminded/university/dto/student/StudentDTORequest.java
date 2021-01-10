package com.foxminded.university.dto.student;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

@Data
public class StudentDTORequest {
    @NotBlank(message = "This field cannot be empty")
    @ApiModelProperty(value = "The student's first name")
    private String firstName;
    @NotBlank(message = "This field cannot be empty")
    @ApiModelProperty(value = "The student's last name")
    private String lastName;
    @Positive(message = "This field must be Positive Number")
    @ApiModelProperty(value = "The student's first name")
    private Integer groupId;
}
