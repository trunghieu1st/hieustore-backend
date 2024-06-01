package com.example.hieustore.service;

import com.example.hieustore.domain.dto.pagination.PaginationFullRequestDto;
import com.example.hieustore.domain.dto.pagination.PaginationResponseDto;
import com.example.hieustore.domain.dto.request.OrderCreateDto;
import com.example.hieustore.domain.dto.request.OrderUpdateDto;
import com.example.hieustore.domain.dto.response.CommonResponseDto;
import com.example.hieustore.domain.dto.response.OrderDto;

public interface OrderService {

    OrderDto getById(String id, String userId);

    PaginationResponseDto<OrderDto> getAllByUserId(String userId, PaginationFullRequestDto paginationFullRequestDto);

    PaginationResponseDto<OrderDto> getAll(PaginationFullRequestDto paginationFullRequestDto);

    OrderDto create(String userId, OrderCreateDto orderCreateDto);

    OrderDto updateById(String id, OrderUpdateDto orderUpdateDto);

    CommonResponseDto deleteById(String id);

}
