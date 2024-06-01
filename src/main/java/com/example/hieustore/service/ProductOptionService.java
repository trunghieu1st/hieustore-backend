package com.example.hieustore.service;

import com.example.hieustore.domain.dto.request.ProductOptionCreateDto;
import com.example.hieustore.domain.dto.request.ProductOptionUpdateDto;
import com.example.hieustore.domain.dto.response.CommonResponseDto;
import com.example.hieustore.domain.dto.response.ProductOptionDto;

import java.util.List;

public interface ProductOptionService {
    ProductOptionDto getById(String id);

    List<ProductOptionDto> getAllByProductId(String productId);
    List<ProductOptionDto> getAll();
    List<ProductOptionDto> getAllByCategoryId(String categoryId);
    ProductOptionDto create(ProductOptionCreateDto productOptionCreateDto);
    ProductOptionDto updateById(String id, ProductOptionUpdateDto productOptionUpdateDto);
    CommonResponseDto deleteById(String id);
}
