package com.ecommerce.computer.service;

import com.ecommerce.computer.model.Order;
import com.ecommerce.computer.model.Product;
import com.ecommerce.computer.model.User;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@Service
public interface ProductService {

    List<Product> findAll();

    Page<Product> findAll(Integer pageNo);
    Page<Product> getByCategoryId(Long categoryId, Integer pageNo);
    Page<Product> getByCategoryName(String categoryName, Integer pageNo);
    Page<Product> getProductSortByLatest(Integer pageNo);
    Page<Product> getProductSortByPrice(Integer pageNo);
    Page<Product> getProductSortByQuantitySold(Integer pageNo);

    Page<Product> searchProduct(String name, Integer pageNo);

    Product findById(Long id);

    Product save(MultipartFile imageProduct, Product product);

    Product update(MultipartFile imageProduct, Product product);

    void deleteById(Long id);

    List<Product> getProductByCategoryId(Long id);

    List<Product> getProductByQuantity(Long quantity);

    Map<String, Long> getProductByCategory();

    Order checkoutProduct(User user, List<Product> products, List<Long> quantitys);

}
