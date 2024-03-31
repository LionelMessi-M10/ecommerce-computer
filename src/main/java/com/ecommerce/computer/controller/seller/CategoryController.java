package com.ecommerce.computer.controller.seller;

import com.ecommerce.computer.model.Category;
import com.ecommerce.computer.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/quan-tri/edit-category")
    public String editCategory(@RequestParam("id") Long id, Model model){
        Category category = categoryService.findById(id);
        model.addAttribute("category", category);
        return "seller/edit-category";
    }

    @PostMapping("/quan-tri/add-category")
    public String addCategory(@ModelAttribute("category") Category category){
        categoryService.addCategory(category);
        return "redirect:category-list";
    }

    @PostMapping("/quan-tri/edit-category")
    public String updateCategory(@ModelAttribute("category") Category category){
        categoryService.updateCategory(category);
        return "redirect:category-list";
    }

    @GetMapping("/quan-tri/delete-category")
    public String deleteCategory(@RequestParam("id") Long id){
        categoryService.deleteCategory(id);
        return "redirect:category-list";
    }

}
