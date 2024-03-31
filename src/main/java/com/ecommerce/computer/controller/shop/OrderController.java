package com.ecommerce.computer.controller.shop;

import com.ecommerce.computer.model.*;
import com.ecommerce.computer.service.CategoryService;
import com.ecommerce.computer.service.OrderService;
import com.ecommerce.computer.service.ProductService;
import com.ecommerce.computer.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
public class OrderController {

    @Autowired
    private UserService userService;
    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private OrderService orderService;


//    xac nhan mua hang tu giao dien xem chi tiet mat hang
    @PostMapping("/ecommerce-computer/shop/checkout")
    public String checkoutProduct(@ModelAttribute("product") Product product, Model model, HttpServletRequest httpServletRequest){
        List<String> quantitys = Collections.singletonList(httpServletRequest.getParameter("orderQuantity"));
        List<String> idProducts = Collections.singletonList(httpServletRequest.getParameter("idProduct"));
        List<Product> products = new ArrayList<>();

        if(product.getId() > 0) products.add(product);

        for(String id : idProducts){
            products.add(productService.findById(Long.parseLong(id)));
        }

        List<Category> categories = categoryService.findAll();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            User user = userService.findByUserName(username);
            Order order = productService.checkoutProduct(user, products, quantitys);

            Double total = 0.0;

            for(OrderDetail item : order.getOrderDetails()) total += item.getTotal();

            model.addAttribute("user", user);
            model.addAttribute("total", total);
            model.addAttribute("categories", categories);
            model.addAttribute("order", order.getOrderDetails());
            model.addAttribute("orderid", order.getId());
        } else {
            return "redirect:/login";
        }
        return "checkout";
    }

    @PostMapping("/ecommerce-computer/shop/order-detail")
    public String orderDetail(Model model, HttpServletRequest httpServletRequest){
        Long orderId = Long.parseLong(httpServletRequest.getParameter("orderid"));
        Order order = orderService.findById(orderId);

        List<Category> categories = categoryService.findAll();

        model.addAttribute("categories", categories);

        return "order_detail";
    }
}
