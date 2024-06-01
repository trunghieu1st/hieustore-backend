package com.example.hieustore.controller;

import com.example.hieustore.base.RestApiV1;
import com.example.hieustore.base.VsResponseUtil;
import com.example.hieustore.constant.UrlConstant;
import com.example.hieustore.domain.dto.request.ProductOptionCreateDto;
import com.example.hieustore.domain.dto.request.ProductOptionUpdateDto;
import com.example.hieustore.service.ProductOptionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestApiV1
public class ProductOptionController {
    private final ProductOptionService productOptionService;

    @Tag(name = "product-option-controller")
    @Operation(summary = "API get product option by id")
    @GetMapping(UrlConstant.Product.GET_OPTION_BY_ID)
    public ResponseEntity<?> getProductOptionById(@PathVariable String id) {
        return VsResponseUtil.success(productOptionService.getById(id));
    }

    @Tag(name = "product-option-controller")
    @Operation(summary = "API get all option by product")
    @GetMapping(UrlConstant.Product.GET_ALL_OPTION)
    public ResponseEntity<?> getAllProductOption(@PathVariable String productId) {
        return VsResponseUtil.success(productOptionService.getAllByProductId(productId));
    }

    @Tag(name = "all-product-option-controller")
    @Operation(summary = "API get all option")
    @GetMapping(UrlConstant.Product.GET_ALL_PRODUCT)
    public ResponseEntity<?> getAll() {
        return VsResponseUtil.success(productOptionService.getAll());
    }

    @Tag(name = "all-product-option-controller")
    @Operation(summary = "API get all option by category")
    @GetMapping(UrlConstant.Product.GET_ALL_BY_CATEGORY)
    public ResponseEntity<?> getAllByCategory(@PathVariable String cateId) {
        return VsResponseUtil.success(productOptionService.getAllByCategoryId(cateId));
    }

    @Tag(name = "product-option-controller")
    @Operation(summary = "API create option of product")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping(UrlConstant.Product.CREATE_OPTION)
    public ResponseEntity<?> createProductOption(@Valid @ModelAttribute ProductOptionCreateDto productOptionCreateDto) {
        return VsResponseUtil.success(productOptionService.create(productOptionCreateDto));
    }

    @Tag(name = "product-option-controller")
    @Operation(summary = "API update product option by id")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PatchMapping(UrlConstant.Product.UPDATE_OPTION)
    public ResponseEntity<?> updateProductOptionById(@PathVariable String id,
                                                     @Valid @ModelAttribute ProductOptionUpdateDto productOptionUpdateDto) {
        return VsResponseUtil.success(productOptionService.updateById(id, productOptionUpdateDto));
    }

    @Tag(name = "product-option-controller")
    @Operation(summary = "API delete product option by id")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping(UrlConstant.Product.DELETE_OPTION)
    public ResponseEntity<?> deleteProductOptionById(@PathVariable String id) {
        return VsResponseUtil.success(productOptionService.deleteById(id));
    }

}
