package com.ecommerce.computer.service.impl;

import com.ecommerce.computer.model.Cart;
import com.ecommerce.computer.model.CartItem;
import com.ecommerce.computer.model.Product;
import com.ecommerce.computer.model.User;
import com.ecommerce.computer.repository.CartItemRepository;
import com.ecommerce.computer.repository.CartRepository;
import com.ecommerce.computer.repository.UserRepository;
import com.ecommerce.computer.service.CartService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    CartItemRepository cartItemRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public void addToCart(User user, Product product) {
        Cart cart = new Cart();
        if(cartRepository.findByUser(user) != null){
            cart = cartRepository.findByUser(user);
        }
        List<CartItem> cartItems = new ArrayList<>();
        if(cart.getCartItems() != null) cartItems = cart.getCartItems();
        CartItem cartItem = new CartItem();
        cartItem.setCart(cart);
        cartItem.setProduct(product);
        cartItem.setQuantity(1L);
        cartItem.setCreated_at(new Date());

        for(CartItem it : cartItems){
            if(it.getProduct() == cartItem.getProduct()){
                it.setQuantity(it.getQuantity() + 1);
                cartRepository.save(cart);
                return;
            }
        }

        cartItems.add(cartItem);
        cart.setCartItems(cartItems);
        cartRepository.save(cart);
    }

    @Override
    public Cart findByUser(User user) {
        return cartRepository.findByUser(user);
    }
}
