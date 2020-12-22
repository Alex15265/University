package com.foxminded.university.dto.professor;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ProfessorDTOResponse {
    @ApiModelProperty(value = "The unique id of the professor")
    private Integer professorId;
    @ApiModelProperty(value = "The professor's first name")
    private String firstName;
    @ApiModelProperty(value = "The professor's last name")
    private String lastName;
}
