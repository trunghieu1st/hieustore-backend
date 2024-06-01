package com.example.hieustore.controller;

import com.example.hieustore.base.RestApiV1;
import com.example.hieustore.base.VsResponseUtil;
import com.example.hieustore.constant.UrlConstant;
import com.example.hieustore.domain.dto.request.CategoryRequestDto;
import com.example.hieustore.domain.mapper.CategoryMapper;
import com.example.hieustore.repository.CategoryRepository;
import com.example.hieustore.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestApiV1
public class CategoryController {
    private final CategoryService categoryService;
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Tag(name = "category-controller")
    @Operation(summary = "API get all category")
    @GetMapping(UrlConstant.Category.GET_ALL)
    public ResponseEntity<?> getAllCategory() {
        return VsResponseUtil.success(categoryService.getAll());
    }

    @Tag(name = "category-controller")
    @Operation(summary = "API get category by id")
    @GetMapping(UrlConstant.Category.GET_BY_ID)
    public ResponseEntity<?> getCategoryById(@PathVariable String id) {
        return VsResponseUtil.success(categoryService.getById(id));
    }

    @Tag(name = "category-controller")
    @Operation(summary = "API create category")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping(UrlConstant.Category.CREATE)
    public ResponseEntity<?> createCategory(@Valid @ModelAttribute CategoryRequestDto createDto) {
        return VsResponseUtil.success(categoryService.create(createDto));
    }

    @Tag(name = "category-controller")
    @Operation(summary = "API update category")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PatchMapping(UrlConstant.Category.UPDATE)
    public ResponseEntity<?> updateCategory(@PathVariable String id, @Valid @ModelAttribute CategoryRequestDto updateDto) {
        return VsResponseUtil.success(categoryService.update(id, updateDto));
    }

    @Tag(name = "category-controller")
    @Operation(summary = "API delete category")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping(UrlConstant.Category.DELETE)
    public ResponseEntity<?> deleteCategory(@PathVariable String id) {
        return VsResponseUtil.success(categoryService.deleteById(id));
    }
}
