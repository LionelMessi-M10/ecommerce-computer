package com.ecommerce.computer.service.impl;

import com.ecommerce.computer.model.Order;
import com.ecommerce.computer.repository.OrderRepository;
import com.ecommerce.computer.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public Page<Order> findAll(Integer pageNo) {
        Pageable pageable = PageRequest.of(pageNo - 1, 8);
        return orderRepository.findAll(pageable);
    }

    @Override
    public Order findById(Long id) {
        return orderRepository.findById(id).get();
    }
}
