package com.example.hieustore.service;

import com.example.hieustore.domain.dto.request.CheckZaloPayStatusRequestDto;
import com.example.hieustore.domain.dto.request.ZaloPayRequestDto;
import org.springframework.http.ResponseEntity;

public interface ZaloPayService {
    ResponseEntity<?> createPayment(ZaloPayRequestDto requestDto);
    ResponseEntity<?> checkStatusOrder(CheckZaloPayStatusRequestDto requestDto);
}