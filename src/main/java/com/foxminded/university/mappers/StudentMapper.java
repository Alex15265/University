package com.foxminded.university.mappers;

import com.foxminded.university.dto.student.StudentDTOResponse;
import com.foxminded.university.entities.Student;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface StudentMapper {
    StudentMapper INSTANCE = Mappers.getMapper(StudentMapper.class);

    @Mapping(source = "group", target = "groupDTOResponse")
    StudentDTOResponse studentToStudentDTOResponse(Student student);
}