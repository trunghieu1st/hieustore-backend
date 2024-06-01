package com.example.hieustore.service;

import com.example.hieustore.domain.dto.pagination.PaginationFullRequestDto;
import com.example.hieustore.domain.dto.pagination.PaginationResponseDto;
import com.example.hieustore.domain.dto.request.NewsRequestDto;
import com.example.hieustore.domain.dto.response.CommonResponseDto;
import com.example.hieustore.domain.dto.response.NewsDto;

public interface NewsService {
    NewsDto getById(String id);

    PaginationResponseDto<NewsDto> getAll(PaginationFullRequestDto paginationFullRequestDto);

    PaginationResponseDto<NewsDto> getByStatus(PaginationFullRequestDto paginationFullRequestDto, Boolean status);

    NewsDto create(NewsRequestDto createDto);

    NewsDto update(String id, NewsRequestDto updateDto);

    CommonResponseDto deleteById(String id);


}
