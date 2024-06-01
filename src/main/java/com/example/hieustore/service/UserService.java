package com.example.hieustore.service;

import com.example.hieustore.domain.dto.pagination.PaginationFullRequestDto;
import com.example.hieustore.domain.dto.pagination.PaginationResponseDto;
import com.example.hieustore.domain.dto.request.ChangePasswordRequestDto;
import com.example.hieustore.domain.dto.request.NewPasswordRequestDto;
import com.example.hieustore.domain.dto.request.UserCreateDto;
import com.example.hieustore.domain.dto.request.UserUpdateDto;
import com.example.hieustore.domain.dto.response.CommonResponseDto;
import com.example.hieustore.domain.dto.response.UserDto;
import com.example.hieustore.domain.entity.User;
import com.example.hieustore.security.UserPrincipal;

public interface UserService {
    User getById(String userId);

    UserDto getUserDtoById(String userId);

    PaginationResponseDto<UserDto> getAll(PaginationFullRequestDto paginationFullRequestDto);

    UserDto getCurrentUser(UserPrincipal user);

    UserDto create(UserCreateDto userCreateDto);

    UserDto update(String id, UserUpdateDto userUpdateDto);

    CommonResponseDto changePassword(String id, ChangePasswordRequestDto changePasswordRequestDto);

    CommonResponseDto createNewPassword(String id, NewPasswordRequestDto newPasswordRequestDto);

    CommonResponseDto deleteById(String id);
}
