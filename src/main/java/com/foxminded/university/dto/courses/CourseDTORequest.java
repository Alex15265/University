package com.foxminded.university.dto.courses;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@Data
public class CourseDTORequest {
    @NotBlank(message = "This field cannot be empty")
    @ApiModelProperty(value = "The course's name")
    private String courseName;
    @Size(min=10, max = 400, message = "Field size must be between 10 and 400")
    @ApiModelProperty(value = "The course's description")
    private String description;
    @Positive(message = "This field must be Positive Number")
    @ApiModelProperty(value = "The unique id of the professor who teaches this course")
    private Integer professorId;
}
