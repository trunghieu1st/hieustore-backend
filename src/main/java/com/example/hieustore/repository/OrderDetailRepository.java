package com.example.hieustore.repository;

import com.example.hieustore.domain.entity.Order;
import com.example.hieustore.domain.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, String> {

    List<OrderDetail> getAllByOrder(Order order);

}
