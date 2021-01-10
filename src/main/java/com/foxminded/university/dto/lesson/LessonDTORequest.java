package com.foxminded.university.dto.lesson;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Positive;

@Data
public class LessonDTORequest {
    @Positive(message = "This field must be Positive Number")
    @ApiModelProperty(value = "The unique id of the professor")
    Integer professorId;
    @Positive(message = "This field must be Positive Number")
    @ApiModelProperty(value = "The unique id of the course")
    Integer courseId;
    @ApiModelProperty(value = "The unique id of the classroom")
    Integer roomId;
    @ApiModelProperty(value = "The unique id of the lesson time")
    Integer timeId;
}
