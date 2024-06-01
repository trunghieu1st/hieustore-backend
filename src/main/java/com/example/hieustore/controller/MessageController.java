package com.example.hieustore.controller;

import com.example.hieustore.base.RestApiV1;
import com.example.hieustore.base.VsResponseUtil;
import com.example.hieustore.constant.UrlConstant;
import com.example.hieustore.domain.dto.pagination.PaginationFullRequestDto;
import com.example.hieustore.domain.dto.request.MessageRequestDto;
import com.example.hieustore.security.CurrentUser;
import com.example.hieustore.security.UserPrincipal;
import com.example.hieustore.service.MessageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestApiV1
public class MessageController {
    private final MessageService messageService;

    @Tag(name = "message-controller")
    @Operation(summary = "API get all message by Page")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_SUPPORT')")
    @GetMapping(UrlConstant.Message.GET_ALL)
    public ResponseEntity<?> getAllMessage(@RequestParam(required = false) String roomId,
                                           @Valid @ParameterObject PaginationFullRequestDto paginationFullRequestDto) {
        return VsResponseUtil.success(messageService.getAll(roomId, paginationFullRequestDto));
    }

    @Tag(name = "message-controller")
    @Operation(summary = "API create message")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_SUPPORT')")
    @PostMapping(UrlConstant.Message.CREATE)
    public ResponseEntity<?> createMessage(@Valid @ModelAttribute MessageRequestDto messageRequestDto,
                                           @Parameter(name = "principal", hidden = true) @CurrentUser UserPrincipal user) {
        return VsResponseUtil.success(messageService.create(user.getId(), messageRequestDto));
    }
}
