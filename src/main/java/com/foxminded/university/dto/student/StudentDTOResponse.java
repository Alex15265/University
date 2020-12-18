package com.foxminded.university.dto.student;

import com.foxminded.university.dto.group.GroupDTOResponse;
import lombok.Data;

@Data
public class StudentDTOResponse {
    private Integer studentId;
    private String firstName;
    private String lastName;
    private GroupDTOResponse groupDTOResponse;
}
