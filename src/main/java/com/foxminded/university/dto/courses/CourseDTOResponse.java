package com.foxminded.university.dto.courses;

import com.foxminded.university.dto.professor.ProfessorDTOResponse;
import lombok.Data;

@Data
public class CourseDTOResponse {
    private Integer courseId;
    private String courseName;
    private String description;
    private ProfessorDTOResponse professorDTOResponse;
}
