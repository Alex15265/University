package com.foxminded.university.mappers;

import com.foxminded.university.dto.professor.ProfessorDTOResponse;
import com.foxminded.university.entities.Professor;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProfessorMapper {
    ProfessorMapper INSTANCE = Mappers.getMapper(ProfessorMapper.class);

    ProfessorDTOResponse professorToProfessorDTOResponse(Professor professor);
}
