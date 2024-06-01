package com.example.hieustore.service;

import com.example.hieustore.domain.dto.request.OrderProductRequestDto;
import com.example.hieustore.domain.dto.response.OrderDetailDto;
import com.example.hieustore.domain.entity.Order;
import com.example.hieustore.domain.entity.OrderDetail;

import java.util.List;

public interface OrderDetailService {

    OrderDetailDto getById(String id, String userId);

    List<OrderDetailDto> getAllByOrderId(String orderId, String userId);

    OrderDetail create(Order order, OrderProductRequestDto orderProductRequestDto);

}
