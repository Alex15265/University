package com.foxminded.university.mappers;

import com.foxminded.university.dto.group.GroupDTOResponse;
import com.foxminded.university.entities.Group;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface GroupMapper {
    GroupMapper INSTANCE = Mappers.getMapper(GroupMapper.class);

    GroupDTOResponse groupToGroupDTOResponse(Group group);
}
