package com.example.hieustore.domain.mapper;

import com.example.hieustore.domain.dto.response.UserRoomDto;
import com.example.hieustore.domain.entity.UserRoom;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserRoomMapper {
    @Mappings({
            @Mapping(target = "userId", source = "user.id"),
            @Mapping(target = "roomId", source = "room.id"),
    })
    UserRoomDto mapUserRoomToUserRoomDto(UserRoom userRoom);

    List<UserRoomDto> mapUserRoomToUserRoomDto(List<UserRoom> userRooms);

}