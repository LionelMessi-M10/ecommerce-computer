package com.ecommerce.computer.service;

import com.ecommerce.computer.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CategoryService {

    List<Category> findAll();
    Page<Category> findAll(Integer pageNo);
    Category findById(Long id);
    void addCategory(Category category);
    void updateCategory(Category category);
    void deleteCategory(Long id);
}
