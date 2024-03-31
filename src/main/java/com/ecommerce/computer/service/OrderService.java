package com.ecommerce.computer.service;

import com.ecommerce.computer.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public interface OrderService {

    Page<Order> findAll(Integer pageNo);
    Order findById(Long id);
}
