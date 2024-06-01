package com.example.hieustore.service;

import com.example.hieustore.domain.dto.pagination.PaginationFullRequestDto;
import com.example.hieustore.domain.dto.pagination.PaginationResponseDto;
import com.example.hieustore.domain.dto.response.RoomDto;

public interface RoomService {
    RoomDto getById(String id);

    PaginationResponseDto<RoomDto> getAll(PaginationFullRequestDto paginationFullRequestDto);

    RoomDto create(String userId);

}