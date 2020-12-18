package com.foxminded.university.dto.lesson;

import com.foxminded.university.dto.classRoom.ClassRoomDTOResponse;
import com.foxminded.university.dto.courses.CourseDTOResponse;
import com.foxminded.university.dto.group.GroupDTOResponse;
import com.foxminded.university.dto.lessonTime.LessonTimeDTOResponse;
import com.foxminded.university.dto.professor.ProfessorDTOResponse;
import lombok.Data;

import java.util.List;

@Data
public class LessonDTOResponse {
    private Integer lessonId;
    private CourseDTOResponse courseDTOResponse;
    private ProfessorDTOResponse professorDTOResponse;
    private ClassRoomDTOResponse classRoomDTOResponse;
    private LessonTimeDTOResponse lessonTimeDTOResponse;
    private List<GroupDTOResponse> groupDTOResponses;
}
