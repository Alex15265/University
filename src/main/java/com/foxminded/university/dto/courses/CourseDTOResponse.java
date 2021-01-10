package com.foxminded.university.dto.courses;

import com.foxminded.university.dto.professor.ProfessorDTOResponse;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CourseDTOResponse {
    @ApiModelProperty(value = "The unique id of the course")
    private Integer courseId;
    @ApiModelProperty(value = "The course's name")
    private String courseName;
    @ApiModelProperty(value = "The course's description")
    private String description;
    private ProfessorDTOResponse professorDTOResponse;
}
