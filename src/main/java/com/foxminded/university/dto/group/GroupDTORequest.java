package com.foxminded.university.dto.group;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class GroupDTORequest {
    @NotBlank(message = "This field cannot be empty")
    @ApiModelProperty(value = "The group's name")
    private String groupName;
}
