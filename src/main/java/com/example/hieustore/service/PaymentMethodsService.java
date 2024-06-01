package com.example.hieustore.service;

import com.example.hieustore.domain.dto.pagination.PaginationFullRequestDto;
import com.example.hieustore.domain.dto.pagination.PaginationResponseDto;
import com.example.hieustore.domain.dto.request.PaymentMethodsRequestDto;
import com.example.hieustore.domain.dto.response.CommonResponseDto;
import com.example.hieustore.domain.dto.response.PaymentMethodsDto;

public interface PaymentMethodsService {
    PaymentMethodsDto getById(String id);

    PaginationResponseDto<PaymentMethodsDto> getAll(PaginationFullRequestDto paginationFullRequestDto);

    PaginationResponseDto<PaymentMethodsDto> getByStatus(PaginationFullRequestDto paginationFullRequestDto, Boolean status);

    PaymentMethodsDto create(PaymentMethodsRequestDto createDto);

    PaymentMethodsDto update(String id, PaymentMethodsRequestDto updateDto);

    CommonResponseDto deleteById(String id);


}
