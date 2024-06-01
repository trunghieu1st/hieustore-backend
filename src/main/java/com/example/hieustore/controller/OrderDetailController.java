package com.example.hieustore.controller;

import com.example.hieustore.base.RestApiV1;
import com.example.hieustore.base.VsResponseUtil;
import com.example.hieustore.constant.UrlConstant;
import com.example.hieustore.security.CurrentUser;
import com.example.hieustore.security.UserPrincipal;
import com.example.hieustore.service.OrderDetailService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RequiredArgsConstructor
@RestApiV1
public class OrderDetailController {
    private final OrderDetailService orderDetailService;

    @Tag(name = "order-detail-controller")
    @Operation(summary = "API get order detail by id")
    @GetMapping(UrlConstant.OrderDetail.GET_BY_ID)
    public ResponseEntity<?> getOrderDetailById(@PathVariable String id,
                                                @Parameter(name = "principal", hidden = true)
                                                @CurrentUser UserPrincipal user) {
        return VsResponseUtil.success(orderDetailService.getById(id, user.getId()));
    }

    @Tag(name = "order-detail-controller")
    @Operation(summary = "API get all order detail by order id")
    @GetMapping(UrlConstant.OrderDetail.GET_ALL)
    public ResponseEntity<?> getAllOrderDetail(@PathVariable String orderId,
                                               @Parameter(name = "principal", hidden = true)
                                               @CurrentUser UserPrincipal user) {
        return VsResponseUtil.success(orderDetailService.getAllByOrderId(orderId, user.getId()));
    }

}
