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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

        if(product.getId() != null) products.add(productService.findById(product.getId()));

        if(!idProducts.isEmpty()){
            for(String id : idProducts){
                if(id != null) products.add(productService.findById(Long.parseLong(id)));
            }
        }

        List<Category> categories = categoryService.findAll();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            User user = userService.findByUserName(username);
            Order order = new Order();
            Double total = 0.0;

            if(!products.isEmpty()){
                order = productService.checkoutProduct(user, products, quantitys);
                for(OrderDetail item : order.getOrderDetails()) total += item.getTotal();
            }

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

    @PostMapping("/ecommerce-computer/shop/order-confirm")
    public String orderDetail(){
        return "redirect:/ecommerce-computer/orders";
    }

    @GetMapping("/ecommerce-computer/orders/order-detail")
    public String viewOrderDetail(@RequestParam("id") Long orderId, Model model){
        Order order = this.orderService.findById(orderId);
        List<Category> categories = this.categoryService.findAll();
        model.addAttribute("orderDetail", order);
        model.addAttribute("buyDate", order.getOrderDetails().get(0).getCreated_at());
        model.addAttribute("categories", categories);
        return "order_detail";
    }
}
