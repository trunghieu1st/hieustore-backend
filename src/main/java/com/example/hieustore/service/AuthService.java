package com.example.hieustore.service;

import com.example.hieustore.domain.dto.request.ForgotPasswordRequestDto;
import com.example.hieustore.domain.dto.request.LoginRequestDto;
import com.example.hieustore.domain.dto.request.TokenRefreshRequestDto;
import com.example.hieustore.domain.dto.request.VerifyRequestDto;
import com.example.hieustore.domain.dto.response.CommonResponseDto;
import com.example.hieustore.domain.dto.response.LoginResponseDto;
import com.example.hieustore.domain.dto.response.TokenRefreshResponseDto;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface AuthService {

    LoginResponseDto login(LoginRequestDto request);

    TokenRefreshResponseDto refresh(TokenRefreshRequestDto request);

    CommonResponseDto logout(HttpServletRequest request,
                             HttpServletResponse response, Authentication authentication);

    CommonResponseDto forgotPassword(ForgotPasswordRequestDto requestDto);

    CommonResponseDto verifyForgotPassword(VerifyRequestDto verifyRequestDto);

}
