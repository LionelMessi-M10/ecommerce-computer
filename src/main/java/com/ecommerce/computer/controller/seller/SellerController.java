package com.ecommerce.computer.controller.seller;

import com.ecommerce.computer.model.Category;
import com.ecommerce.computer.model.Order;
import com.ecommerce.computer.model.Product;
import com.ecommerce.computer.service.CategoryService;
import com.ecommerce.computer.service.OrderService;
import com.ecommerce.computer.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SellerController {

    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private OrderService orderService;

    @GetMapping("/quan-tri")
    public String seller(){
        return "seller/index";
    }

    @GetMapping("/quan-tri/product-list")
    public String productList(Model model, @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo){
        Page<Product> productPage = productService.findAll(pageNo);
        model.addAttribute("totalPage", productPage.getTotalPages());
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("productPage", productPage);
        return "seller/product-list";
    }

    @GetMapping("/quan-tri/category-list")
    public String categoryList(Model model, Category category, @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo){
        Page<Category> categoryPage = categoryService.findAll(pageNo);
        model.addAttribute("category", category);
        model.addAttribute("categoryPage", categoryPage);
        model.addAttribute("totalPage", categoryPage.getTotalPages());
        model.addAttribute("currentPage", pageNo);
        return "seller/categories";
    }

//    Don duyet
    @GetMapping("/quan-tri/order-list")
    public String approvedOrder(Model model, @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo){
        Page<Order> orderPage = orderService.findAll(pageNo);
        model.addAttribute("totalPage", orderPage.getTotalPages());
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("orderPage", orderPage);
        return "seller/approved-order";
    }

}
