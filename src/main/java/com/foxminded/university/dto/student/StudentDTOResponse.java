package com.foxminded.university.dto.student;

import com.foxminded.university.dto.group.GroupDTOResponse;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class StudentDTOResponse {
    @ApiModelProperty(value = "The unique id of the student")
    private Integer studentId;
    @ApiModelProperty(value = "The student's first name")
    private String firstName;
    @ApiModelProperty(value = "The student's last name")
    private String lastName;
    private GroupDTOResponse groupDTOResponse;
}
