package com.example.hieustore.domain.mapper;

import com.example.hieustore.domain.dto.response.RoomDto;
import com.example.hieustore.domain.entity.Room;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface RoomMapper {
    RoomDto mapRoomToRoomDto(Room room);

    List<RoomDto> mapRoomToRoomDto(List<Room> rooms);
}