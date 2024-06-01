package com.example.hieustore.service;

import com.example.hieustore.domain.dto.pagination.PaginationFullRequestDto;
import com.example.hieustore.domain.dto.pagination.PaginationResponseDto;
import com.example.hieustore.domain.dto.request.ReviewCreateDto;
import com.example.hieustore.domain.dto.request.ReviewUpdateDto;
import com.example.hieustore.domain.dto.response.CommonResponseDto;
import com.example.hieustore.domain.dto.response.ReviewDto;

public interface ReviewService {

    ReviewDto getById(String id);

    PaginationResponseDto<ReviewDto> search(String productId, Integer star, PaginationFullRequestDto paginationFullRequestDto);

    ReviewDto create(String userId, ReviewCreateDto reviewCreateDto);

    ReviewDto updateById(String userId, String id, ReviewUpdateDto reviewUpdateDto);

    CommonResponseDto deleteById(String userId, String id);

}
