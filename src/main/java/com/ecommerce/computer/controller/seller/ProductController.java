package com.ecommerce.computer.controller.seller;

import com.ecommerce.computer.model.Category;
import com.ecommerce.computer.model.Product;
import com.ecommerce.computer.service.CategoryService;
import com.ecommerce.computer.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
public class ProductController {

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ProductService productService;

    @GetMapping("/quan-tri/add-product")
    public String addProductPage(Model model, Product product) {
        List<Category> categories = categoryService.findAll();
        model.addAttribute("categories", categories);
        model.addAttribute("product", product);
        return "seller/add-product";
    }

    @PostMapping("/quan-tri/add-product")
    public String saveProduct(@ModelAttribute("product") Product product,
                              @RequestParam("imageProduct") MultipartFile imageProduct) {
        productService.save(imageProduct, product);
        return "redirect:/product-list";
    }

    @GetMapping("/quan-tri/edit-product")
    public String editProduct(@RequestParam("id") Long id, Model model){
        Product product = productService.findById(id);
        List<Category> categories = categoryService.findAll();
        model.addAttribute("product", product);
        model.addAttribute("categories", categories);
        return "seller/edit-product";
    }

    @PostMapping("/quan-tri/edit-product")
    public String updateProduct(@ModelAttribute("product") Product product,
                                @RequestParam("imageProduct") MultipartFile imageProduct){
        productService.update(imageProduct, product);
        return "redirect:/product-list";
    }

    @GetMapping("/quan-tri/delete-product")
    public String deleteProduct(@RequestParam("id") Long id){
        productService.deleteById(id);
        return "redirect:/product-list";
    }
}
