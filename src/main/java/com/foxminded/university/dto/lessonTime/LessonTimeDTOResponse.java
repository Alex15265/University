package com.foxminded.university.dto.lessonTime;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class LessonTimeDTOResponse {
    @ApiModelProperty(value = "The unique id of the lessontime")
    private Integer timeId;
    @ApiModelProperty(value = "The lesson start time")
    private LocalDateTime lessonStart;
    @ApiModelProperty(value = "The lesson end time")
    private LocalDateTime lessonEnd;
}
