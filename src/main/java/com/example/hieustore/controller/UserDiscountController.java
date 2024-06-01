package com.example.hieustore.controller;

import com.example.hieustore.base.RestApiV1;
import com.example.hieustore.base.VsResponseUtil;
import com.example.hieustore.constant.UrlConstant;
import com.example.hieustore.domain.dto.pagination.PaginationFullRequestDto;
import com.example.hieustore.domain.dto.request.UserDiscountCreateDto;
import com.example.hieustore.security.CurrentUser;
import com.example.hieustore.security.UserPrincipal;
import com.example.hieustore.service.UserDiscountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestApiV1
public class UserDiscountController {
    private final UserDiscountService userDiscountService;

    @Tag(name = "user-discount-controller")
    @Operation(summary = "API get user discount by id")
    @GetMapping(UrlConstant.UserDiscount.GET_BY_ID)
    public ResponseEntity<?> getUserDiscountCodeById(@PathVariable String id) {
        return VsResponseUtil.success(userDiscountService.getById(id));
    }

    @Tag(name = "user-discount-controller")
    @Operation(summary = "API get all user discount")
    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping(UrlConstant.UserDiscount.GET_ALL)
    public ResponseEntity<?> getAllUserDiscountCode(@RequestParam(required = false) Boolean status,
                                                    @Valid @ParameterObject PaginationFullRequestDto paginationFullRequestDto) {
        return VsResponseUtil.success(userDiscountService.getAll(status, paginationFullRequestDto));
    }

    @Tag(name = "user-discount-controller")
    @Operation(summary = "API get all discount by user id")
    @GetMapping(UrlConstant.UserDiscount.GET_BY_USER_ID)
    public ResponseEntity<?> getAllByUserId(@Parameter(name = "principal", hidden = true)
                                            @CurrentUser UserPrincipal user,
                                            @Valid @ParameterObject PaginationFullRequestDto paginationFullRequestDto,
                                            @RequestParam(required = false) Boolean type, @RequestParam(required = false) Boolean status) {
        return VsResponseUtil.success(userDiscountService.getAllByUserId(user.getId(), type, status, paginationFullRequestDto));
    }

    @Tag(name = "user-discount-controller")
    @Operation(summary = "API create user discount code")
    @PostMapping(UrlConstant.UserDiscount.CREATE)
    public ResponseEntity<?> createUserDiscountCode(@Parameter(name = "principal", hidden = true)
                                                    @CurrentUser UserPrincipal user, @Valid @RequestBody UserDiscountCreateDto createDto) {
        return VsResponseUtil.success(userDiscountService.create(user.getId(), createDto));
    }

    @Tag(name = "user-discount-controller")
    @Operation(summary = "API add user discount by code")
    @PostMapping(UrlConstant.UserDiscount.ADD)
    public ResponseEntity<?> addUserDiscountByCode(@Parameter(name = "principal", hidden = true)
                                                   @CurrentUser UserPrincipal user, @Valid @RequestParam String code) {
        return VsResponseUtil.success(userDiscountService.addDiscountCode(user.getId(), code));
    }

    @Tag(name = "user-discount-controller")
    @Operation(summary = "API delete user discount code")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping(UrlConstant.UserDiscount.DELETE)
    public ResponseEntity<?> deleteUserDiscountCode(@PathVariable String id) {
        return VsResponseUtil.success(userDiscountService.deleteById(id));
    }
}
