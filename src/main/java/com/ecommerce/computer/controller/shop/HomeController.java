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
public class HomeController {

    @Autowired
    private UserService userService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ProductService productService;
    @Autowired
    private CartService cartService;

    @GetMapping("/")
    public String trangChu(){
        return "redirect:/ecommerce-computer/home";
    }

    @GetMapping("/ecommerce-computer/home")
    public String home(Model model){
        List<Category> categories = categoryService.findAll();
        List<Product> productLimit6 = productService.getProductByQuantity(6L);
        List<Product> productLimit8 = productService.getProductByQuantity(8L);
        List<Product> products = productService.findAll();

        model.addAttribute("categories", categories);
        model.addAttribute("productlimit", productLimit6);
        model.addAttribute("producteight", productLimit8);
        model.addAttribute("products", products);
        return "index";
    }

    @GetMapping("/ecommerce-computer/shop")
    public String shop(Model model, @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo){
        Page<Product> productPage = productService.findAll(pageNo);
        List<Category> categories = categoryService.findAll();
        List<Product> products = productService.findAll();
        Map<String, Long> filterCategory = productService.getProductByCategory();

        model.addAttribute("totalPage", productPage.getTotalPages());
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("productPage", productPage);
        model.addAttribute("categories", categories);
        model.addAttribute("category_size", products.size());
        model.addAttribute("filter_category", filterCategory);
        return "shop";
    }

    @GetMapping("/ecommerce-computer/orders")
    public String detail(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<Category> categories = categoryService.findAll();

        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            User user = userService.findByUserName(username);
            List<Order> order = user.getOrders();

            List<Order> orders = new ArrayList<>();

            if(order != null) orders = order;

            model.addAttribute("orders",  orders);
            model.addAttribute("categories", categories);
        } else {
            return "redirect:/login";
        }

        return "orders";
    }

    @GetMapping("/ecommerce-computer/cart")
    public String cart(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<Category> categories = categoryService.findAll();

        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            User user = userService.findByUserName(username);
            Cart cart = user.getCart();

            List<CartItem> cartItems = new ArrayList<>();

            if(cart != null) cartItems = cart.getCartItems();

            double total = 0.0;

            for(CartItem it : cartItems) total += it.getQuantity() * it.getProduct().getPrice();

            model.addAttribute("total", total);
            model.addAttribute("cartItems",  cartItems);
            model.addAttribute("categories", categories);
        } else {
            return "redirect:/login";
        }

        return "cart";
    }

    @GetMapping("/ecommerce-computer/checkout")
    public String checkout(){
        return "checkout";
    }

    @GetMapping("/ecommerce-computer/contact")
    public String contact(){
        return "contact";
    }

    // Lay san pham theo category tra ve trang shop
    @GetMapping("/ecommerce-computer/shop/category")
    public String productsFromCategory(@RequestParam("id") Long id, Model model, @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo){
        Page<Product> productPage = productService.getByCategoryId(id, pageNo);
        List<Category> categories = categoryService.findAll();
        Map<String, Long> filterCategory = productService.getProductByCategory();

        model.addAttribute("totalPage", productPage.getTotalPages());
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("productPage", productPage);
        model.addAttribute("categories", categories);
        model.addAttribute("filter_category", filterCategory);
        return "shop";
    }

    @GetMapping("/ecommerce-computer/shop/category-name")
    public String productByCategoryName(@RequestParam("name") String categoryName, Model model, @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo){
        Page<Product> productPage = productService.getByCategoryName(categoryName, pageNo);
        List<Category> categories = categoryService.findAll();
        Map<String, Long> filterCategory = productService.getProductByCategory();

        model.addAttribute("totalPage", productPage.getTotalPages());
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("productPage", productPage);
        model.addAttribute("categories", categories);
        model.addAttribute("filter_category", filterCategory);

        return "shop";
    }

    // Lay san pham theo id tra ve trang detail
    @GetMapping("/ecommerce-computer/shop/product-detail")
    public String productById(@RequestParam("id") Long id, Model model){
        Product product = productService.findById(id);
        List<Category> categories = categoryService.findAll();

        model.addAttribute("product", product);
        model.addAttribute("categories", categories);
        return "detail";
    }

    @GetMapping("/ecommerce-computer/shop/search")
    public String searchProduct(@RequestParam("name") String name, Model model, @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo){
        Page<Product> productPage = productService.searchProduct(name, pageNo);
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
