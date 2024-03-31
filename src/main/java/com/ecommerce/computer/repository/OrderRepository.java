package com.ecommerce.computer.repository;

import com.ecommerce.computer.model.Order;
import com.ecommerce.computer.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByUser(User user);
}
