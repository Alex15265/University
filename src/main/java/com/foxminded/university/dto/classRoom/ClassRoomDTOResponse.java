package com.foxminded.university.dto.classRoom;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ClassRoomDTOResponse {
    @ApiModelProperty(value = "The unique id of the classroom")
    private Integer roomId;
    @ApiModelProperty(value = "The classroom's number")
    private Integer roomNumber;
}
