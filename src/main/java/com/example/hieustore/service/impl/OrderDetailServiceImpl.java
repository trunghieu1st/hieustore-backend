package com.example.hieustore.service.impl;

import com.example.hieustore.constant.ErrorMessage;
import com.example.hieustore.constant.RoleConstant;
import com.example.hieustore.domain.dto.request.OrderProductRequestDto;
import com.example.hieustore.domain.dto.response.OrderDetailDto;
import com.example.hieustore.domain.entity.Order;
import com.example.hieustore.domain.entity.OrderDetail;
import com.example.hieustore.domain.entity.ProductOption;
import com.example.hieustore.domain.entity.User;
import com.example.hieustore.domain.mapper.OrderDetailMapper;
import com.example.hieustore.exception.ForbiddenException;
import com.example.hieustore.exception.NotFoundException;
import com.example.hieustore.repository.OrderDetailRepository;
import com.example.hieustore.repository.OrderRepository;
import com.example.hieustore.repository.ProductOptionRepository;
import com.example.hieustore.service.OrderDetailService;
import com.example.hieustore.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderDetailServiceImpl implements OrderDetailService {

    private final OrderDetailRepository orderDetailRepository;
    private final OrderRepository orderRepository;
    private final ProductOptionRepository productOptionRepository;
    private final OrderDetailMapper orderDetailMapper;
    private final UserService userService;

    @Override
    public OrderDetailDto getById(String id, String userId) {
        User user = userService.getById(userId);
        OrderDetail orderDetail = orderDetailRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.Order.ERR_NOT_FOUND_ID, new String[]{id}));
        if (!orderDetail.getCreatedBy().equals(userId) && !user.getRole().getName().equals(RoleConstant.ADMIN)) {
            throw new ForbiddenException(ErrorMessage.FORBIDDEN);
        }
        return orderDetailMapper.mapOrderDetailToOrderDetailDto(orderDetail);
    }

    @Override
    public List<OrderDetailDto> getAllByOrderId(String orderId, String userId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.Order.ERR_NOT_FOUND_ID, new String[]{orderId}));
        User user = userService.getById(userId);
        if (!order.getCreatedBy().equals(userId) && !user.getRole().getName().equals(RoleConstant.ADMIN)) {
            throw new ForbiddenException(ErrorMessage.FORBIDDEN);
        }
        List<OrderDetail> orderDetails = orderDetailRepository.getAllByOrder(order);
        return orderDetailMapper.mapOrderDetailsToOrderDetailDtos(orderDetails);
    }

    @Override
    public OrderDetail create(Order order, OrderProductRequestDto orderProductRequestDto) {
        ProductOption productOption = productOptionRepository.findById(orderProductRequestDto.getProductOptionId())
                .orElseThrow(() -> new NotFoundException(ErrorMessage.ProductOption.ERR_NOT_FOUND_ID, new String[]{orderProductRequestDto.getProductOptionId()}));
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setOrder(order);
        orderDetail.setProductOption(productOption);
        orderDetail.setQuantity(orderProductRequestDto.getQuantity());
        orderDetail.setPrice(productOption.getPrice());
        return orderDetailRepository.save(orderDetail);
    }

}
