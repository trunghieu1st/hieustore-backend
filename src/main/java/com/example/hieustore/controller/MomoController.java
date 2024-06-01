package com.example.hieustore.controller;

import com.example.hieustore.base.RestApiV1;
import com.example.hieustore.constant.UrlConstant;
import com.example.hieustore.domain.dto.request.CheckMomoStatusRequestDto;
import com.example.hieustore.domain.dto.request.MomoRequestDto;
import com.example.hieustore.service.MomoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RestApiV1
@RequiredArgsConstructor
public class MomoController {

    private final MomoService paymentService;

    @Tag(name = "momo create transaction")
    @Operation(summary = "API get all product")
    @PostMapping(UrlConstant.Payment.CREATE)
    public ResponseEntity<?> createPayment(@RequestBody MomoRequestDto requestDto) {
        return paymentService.createPayment(requestDto);
    }

    @Tag(name = "momo create transaction")
    @Operation(summary = "API get all product")
    @PostMapping(UrlConstant.Payment.CHECK)
    public ResponseEntity<?> checkStatusTransaction(@RequestBody CheckMomoStatusRequestDto requestDto) {
        return paymentService.checkStatusTransaction(requestDto);
    }
}