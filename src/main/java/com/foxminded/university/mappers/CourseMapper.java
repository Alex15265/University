package com.foxminded.university.mappers;

import com.foxminded.university.dto.courses.CourseDTOResponse;
import com.foxminded.university.entities.Course;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CourseMapper {
    CourseMapper INSTANCE = Mappers.getMapper(CourseMapper.class);

    @Mapping(source = "professor", target = "professorDTOResponse")
    CourseDTOResponse courseToCourseDTOResponse(Course course);
}
