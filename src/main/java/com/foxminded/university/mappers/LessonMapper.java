package com.foxminded.university.mappers;

import com.foxminded.university.dto.lesson.LessonDTOResponse;
import com.foxminded.university.entities.Lesson;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface LessonMapper {
    LessonMapper INSTANCE = Mappers.getMapper(LessonMapper.class);

    @Mapping(source = "professor", target = "professorDTOResponse")
    @Mapping(source = "course", target = "courseDTOResponse")
    @Mapping(source = "classRoom", target = "classRoomDTOResponse")
    @Mapping(source = "time", target = "lessonTimeDTOResponse")
    @Mapping(source = "groups", target = "groupDTOResponses")
    LessonDTOResponse lessonToLessonDTOResponse(Lesson lesson);
}
