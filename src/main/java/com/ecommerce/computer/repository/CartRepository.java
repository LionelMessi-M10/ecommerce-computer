package com.ecommerce.computer.repository;

import com.ecommerce.computer.model.Cart;
import com.ecommerce.computer.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    Cart findByUser(User user);
}
