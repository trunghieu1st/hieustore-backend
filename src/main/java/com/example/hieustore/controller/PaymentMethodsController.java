package com.example.hieustore.controller;

import com.example.hieustore.base.RestApiV1;
import com.example.hieustore.base.VsResponseUtil;
import com.example.hieustore.constant.UrlConstant;
import com.example.hieustore.domain.dto.pagination.PaginationFullRequestDto;
import com.example.hieustore.domain.dto.request.PaymentMethodsRequestDto;
import com.example.hieustore.service.PaymentMethodsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestApiV1
public class PaymentMethodsController {
    private final PaymentMethodsService paymentMethodsService;

    @Tag(name = "PaymentMethods-controller")
    @Operation(summary = "API get PaymentMethods by id")
    @GetMapping(UrlConstant.PaymentMethods.GET_BY_ID)
    public ResponseEntity<?> getPaymentMethodsById(@PathVariable String id) {
        return VsResponseUtil.success(paymentMethodsService.getById(id));
    }

    @Tag(name = "PaymentMethods-controller")
    @Operation(summary = "API get all PaymentMethods")
    // @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping(UrlConstant.PaymentMethods.GET_ALL)
    public ResponseEntity<?> getAllPaymentMethods(@Valid @ParameterObject PaginationFullRequestDto paginationFullRequestDto) {
        return VsResponseUtil.success(paymentMethodsService.getAll(paginationFullRequestDto));
    }

    @Tag(name = "PaymentMethods-controller")
    @Operation(summary = "API get PaymentMethods by status")
    @GetMapping(UrlConstant.PaymentMethods.GET_BY_STATUS)
    public ResponseEntity<?> getPaymentMethodsByStatus(@Valid @ParameterObject PaginationFullRequestDto paginationFullRequestDto,
                                             @RequestParam(required = false, defaultValue = "true") Boolean status) {
        return VsResponseUtil.success(paymentMethodsService.getByStatus(paginationFullRequestDto, status));
    }

    @Tag(name = "PaymentMethods-controller")
    @Operation(summary = "API create PaymentMethods")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping(UrlConstant.PaymentMethods.CREATE)
    public ResponseEntity<?> createPaymentMethods(@Valid @ModelAttribute PaymentMethodsRequestDto createDto) {
        return VsResponseUtil.success(paymentMethodsService.create(createDto));
    }

    @Tag(name = "PaymentMethods-controller")
    @Operation(summary = "API update PaymentMethods")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PatchMapping(UrlConstant.PaymentMethods.UPDATE)
    public ResponseEntity<?> updatePaymentMethods(@PathVariable String id, @Valid @ModelAttribute PaymentMethodsRequestDto updateDto) {
        return VsResponseUtil.success(paymentMethodsService.update(id, updateDto));
    }

    @Tag(name = "PaymentMethods-controller")
    @Operation(summary = "API delete PaymentMethods")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping(UrlConstant.PaymentMethods.DELETE)
    public ResponseEntity<?> deletePaymentMethods(@PathVariable String id) {
        return VsResponseUtil.success(paymentMethodsService.deleteById(id));
    }
}
