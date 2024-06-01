package com.example.hieustore.service;

import com.example.hieustore.domain.dto.pagination.PaginationFullRequestDto;
import com.example.hieustore.domain.dto.pagination.PaginationResponseDto;
import com.example.hieustore.domain.dto.request.ProductRequestDto;
import com.example.hieustore.domain.dto.response.CommonResponseDto;
import com.example.hieustore.domain.dto.response.ProductDto;
import com.example.hieustore.domain.dto.response.ProductSimpleDto;

public interface ProductService {
    ProductDto getById(String id);

    PaginationResponseDto<ProductSimpleDto> getAll(String categoryId, PaginationFullRequestDto paginationFullRequestDto);

    PaginationResponseDto<ProductSimpleDto> search(PaginationFullRequestDto paginationFullRequestDto);

    ProductDto create(ProductRequestDto productRequestDto);

    ProductDto updateById(String id, ProductRequestDto productRequestDto);

    CommonResponseDto deleteById(String id);
}
