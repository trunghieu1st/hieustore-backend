package com.example.hieustore.service;

import com.example.hieustore.domain.dto.pagination.PaginationFullRequestDto;
import com.example.hieustore.domain.dto.pagination.PaginationResponseDto;
import com.example.hieustore.domain.dto.request.DiscountCodeRequestDto;
import com.example.hieustore.domain.dto.response.CommonResponseDto;
import com.example.hieustore.domain.dto.response.DiscountCodeDto;

public interface DiscountCodeService {

    DiscountCodeDto getById(String id);

    PaginationResponseDto<DiscountCodeDto> getAll(Boolean type, PaginationFullRequestDto paginationFullRequestDto);

    DiscountCodeDto create(DiscountCodeRequestDto createDto);

    DiscountCodeDto update(String id, DiscountCodeRequestDto updateDto);

    CommonResponseDto deleteById(String id);

}
