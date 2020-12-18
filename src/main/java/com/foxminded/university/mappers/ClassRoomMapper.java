package com.foxminded.university.mappers;

import com.foxminded.university.dto.classRoom.ClassRoomDTOResponse;
import com.foxminded.university.entities.ClassRoom;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ClassRoomMapper {
    ClassRoomMapper INSTANCE = Mappers.getMapper(ClassRoomMapper.class);

    ClassRoomDTOResponse classRoomToClassRoomDTOResponse(ClassRoom classRoom);
}
