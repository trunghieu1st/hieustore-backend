package com.example.hieustore.service;

import com.example.hieustore.domain.dto.pagination.PaginationFullRequestDto;
import com.example.hieustore.domain.dto.pagination.PaginationResponseDto;
import com.example.hieustore.domain.dto.request.SlideRequestDto;
import com.example.hieustore.domain.dto.response.CommonResponseDto;
import com.example.hieustore.domain.dto.response.SlideDto;

public interface SlideService {
    SlideDto getById(String id);

    PaginationResponseDto<SlideDto> getAll(PaginationFullRequestDto paginationFullRequestDto);

    PaginationResponseDto<SlideDto> getByStatus(PaginationFullRequestDto paginationFullRequestDto, Boolean status);

    SlideDto create(SlideRequestDto createDto);

    SlideDto update(String id, SlideRequestDto updateDto);

    CommonResponseDto deleteById(String id);
}
