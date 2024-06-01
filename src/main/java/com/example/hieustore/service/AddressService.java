package com.example.hieustore.service;

import com.example.hieustore.domain.dto.request.AddressRequestDto;
import com.example.hieustore.domain.dto.response.AddressDto;
import com.example.hieustore.domain.dto.response.CommonResponseDto;

import java.util.List;

public interface AddressService {
    AddressDto getById(String id, String userId);

    List<AddressDto> getAllByCurrentUserId(String userId);

    List<AddressDto> getAllByUserId(String userId);

    AddressDto getDefaultByCurrentUser(String userId);

    AddressDto create(AddressRequestDto addressRequestDto, String userId);

    AddressDto updateById(String id, AddressRequestDto addressRequestDto, String userId);

    AddressDto changeDefaultById(String id, String userId);

    CommonResponseDto deleteById(String id, String userId);
}
