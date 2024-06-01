package com.example.hieustore.service;

import com.example.hieustore.domain.dto.pagination.PaginationFullRequestDto;
import com.example.hieustore.domain.dto.pagination.PaginationResponseDto;
import com.example.hieustore.domain.dto.request.CartCreateDto;
import com.example.hieustore.domain.dto.request.CartUpdateDto;
import com.example.hieustore.domain.dto.response.CartDto;
import com.example.hieustore.domain.dto.response.CommonResponseDto;

public interface CartService {

    PaginationResponseDto<CartDto> getAll(String userId, PaginationFullRequestDto paginationFullRequestDto);

    int getNumberItem(String userId);

    CartDto create(String userId, CartCreateDto cartCreateDto);

    CartDto updateById(String userId, String id, CartUpdateDto cartUpdateDto);

    CommonResponseDto deleteById(String userId, String id);

}
