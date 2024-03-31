package com.ecommerce.computer.controller.shop;

import com.ecommerce.computer.model.*;
import com.ecommerce.computer.service.CartService;
import com.ecommerce.computer.service.CategoryService;
import com.ecommerce.computer.service.ProductService;
import com.ecommerce.computer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class CartController {
    @Autowired
    private CartService cartService;
    @Autowired
    private UserService userService;
    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;

//    Them vao gio hang tu giao dien xem chi tiet mat hang
    @GetMapping("/ecommerce-computer/cart/product")
    public String addToCart(@RequestParam("id") Long id, Model model){
        Product product = productService.findById(id);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<Category> categories = categoryService.findAll();

        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            User user = userService.findByUserName(username);
            cartService.addToCart(user, product);

            Cart cart = cartService.findByUser(user);
            List<CartItem> cartItems = new ArrayList<>();

            if(cart != null) cartItems = cart.getCartItems();

            double total = 0.0;

            for (CartItem it : cartItems) total += it.getQuantity() * it.getProduct().getPrice();

            model.addAttribute("total", total);
            model.addAttribute("cartItems",  cartItems);
            model.addAttribute("categories", categories);
        } else {
            return "redirect:/login";
        }
        return "cart";
    }

    @GetMapping("/ecommerce-computer/shop/sortByLatest")
    public String productSortByLatest(Model model, @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo){
        Page<Product> productPage = productService.getProductSortByLatest(pageNo);
        List<Category> categories = categoryService.findAll();
        Map<String, Long> filterCategory = productService.getProductByCategory();

        model.addAttribute("totalPage", productPage.getTotalPages());
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("productPage", productPage);
        model.addAttribute("categories", categories);
        model.addAttribute("filter_category", filterCategory);

        return "shop";
    }

    @GetMapping("/ecommerce-computer/shop/sortByPrice")
    public String productSortByPrice(Model model, @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo){
        Page<Product> productPage = productService.getProductSortByPrice(pageNo);
        List<Category> categories = categoryService.findAll();
        Map<String, Long> filterCategory = productService.getProductByCategory();

        model.addAttribute("totalPage", productPage.getTotalPages());
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("productPage", productPage);
        model.addAttribute("categories", categories);
        model.addAttribute("filter_category", filterCategory);

        return "shop";
    }

    @GetMapping("/ecommerce-computer/shop/sortByQuantitySold")
    public String productSortByQuantitySold(Model model, @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo){
        Page<Product> productPage = productService.getProductSortByQuantitySold(pageNo);
        List<Category> categories = categoryService.findAll();
        Map<String, Long> filterCategory = productService.getProductByCategory();

        model.addAttribute("totalPage", productPage.getTotalPages());
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("productPage", productPage);
        model.addAttribute("categories", categories);
        model.addAttribute("filter_category", filterCategory);

        return "shop";
    }
}
