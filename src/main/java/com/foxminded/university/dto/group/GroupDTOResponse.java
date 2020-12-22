package com.foxminded.university.dto.group;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class GroupDTOResponse {
    @ApiModelProperty(value = "The unique id of the group")
    private Integer groupId;
    @ApiModelProperty(value = "The group's name")
    private String groupName;
}
