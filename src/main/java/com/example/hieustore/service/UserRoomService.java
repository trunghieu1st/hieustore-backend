package com.example.hieustore.service;

import com.example.hieustore.domain.dto.pagination.PaginationFullRequestDto;
import com.example.hieustore.domain.dto.pagination.PaginationResponseDto;
import com.example.hieustore.domain.dto.response.UserRoomDto;
import com.example.hieustore.domain.entity.UserRoomId;

public interface UserRoomService {
    UserRoomDto getById(UserRoomId userRoomId);

    PaginationResponseDto<UserRoomDto> getAll(String userId, PaginationFullRequestDto paginationFullRequestDto);

    UserRoomDto create(String roomId, String userId);

}
