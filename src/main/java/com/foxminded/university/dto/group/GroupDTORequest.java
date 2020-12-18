package com.foxminded.university.dto.group;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class GroupDTORequest {
    @NotBlank(message = "This field cannot be empty")
    private String groupName;
}
