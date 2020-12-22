package com.foxminded.university.dto.classRoom;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class ClassRoomDTORequest {
    @Min(value = 100, message = "Room Number cannot be less than 100") @NotNull(message = "This field cannot be empty")
    @ApiModelProperty(value = "The classroom's number")
    private Integer roomNumber;
}
