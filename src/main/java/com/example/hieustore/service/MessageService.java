package com.example.hieustore.service;

import com.example.hieustore.domain.dto.pagination.PaginationFullRequestDto;
import com.example.hieustore.domain.dto.pagination.PaginationResponseDto;
import com.example.hieustore.domain.dto.request.MessageRequestDto;
import com.example.hieustore.domain.dto.response.MessageDto;

public interface MessageService {
    PaginationResponseDto<MessageDto> getAll(String roomId, PaginationFullRequestDto paginationFullRequestDto);

    MessageDto create(String userId, MessageRequestDto messageRequestDto);
}