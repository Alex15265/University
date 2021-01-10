package com.foxminded.university.mappers;

import com.foxminded.university.dto.lessonTime.LessonTimeDTOResponse;
import com.foxminded.university.entities.LessonTime;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface LessonTimeMapper {
    LessonTimeMapper INSTANCE = Mappers.getMapper(LessonTimeMapper.class);

    LessonTimeDTOResponse lessonTimeToLessonTimeDTOResponse(LessonTime lessonTime);
}
