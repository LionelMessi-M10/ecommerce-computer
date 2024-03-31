package com.ecommerce.computer.service;

import com.ecommerce.computer.model.Cart;
import com.ecommerce.computer.model.Product;
import com.ecommerce.computer.model.User;
import org.springframework.stereotype.Service;

@Service
public interface CartService {

    void addToCart(User user, Product product);
    Cart findByUser(User user);
}
