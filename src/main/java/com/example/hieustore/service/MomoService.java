package com.example.hieustore.service;

import com.example.hieustore.domain.dto.request.CheckMomoStatusRequestDto;
import com.example.hieustore.domain.dto.request.MomoRequestDto;
import org.springframework.http.ResponseEntity;

public interface MomoService {
    ResponseEntity<?> createPayment(MomoRequestDto requestDto);
    ResponseEntity<?> checkStatusTransaction(CheckMomoStatusRequestDto requestDto);
}
