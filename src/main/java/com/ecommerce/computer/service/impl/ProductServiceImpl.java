package com.ecommerce.computer.service.impl;

import com.ecommerce.computer.model.*;
import com.ecommerce.computer.repository.CategoryRepository;
import com.ecommerce.computer.repository.OrderRepository;
import com.ecommerce.computer.repository.ProductRepository;
import com.ecommerce.computer.service.ProductService;
import com.ecommerce.computer.utils.ImageUpload;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ImageUpload imageUpload;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public Page<Product> findAll(Integer pageNo) {
        Pageable pageable = PageRequest.of(pageNo - 1, 9);
        return productRepository.findAll(pageable);
    }

    @Override
    public Page<Product> getByCategoryId(Long categoryId, Integer pageNo) {
        Pageable pageable = PageRequest.of(pageNo - 1, 9);
        List<Product> productList = productRepository.getProductByCategoryId(categoryId);

        Page<Product> products = new PageImpl<>(productList, pageable, productList.size());

        return products;
    }

    @Override
    public Page<Product> getByCategoryName(String categoryName, Integer pageNo) {
        Pageable pageable = PageRequest.of(pageNo - 1, 9);
        List<Product> productList = productRepository.getProductByCategoryName(categoryName);

        Page<Product> products = new PageImpl<>(productList, pageable, productList.size());

        return products;
    }

    @Override
    public Page<Product> getProductSortByLatest(Integer pageNo) {
        Pageable pageable = PageRequest.of(pageNo - 1, 9);
        List<Product> productList = productRepository.getProductListSortByLatest();

        Page<Product> products = new PageImpl<>(productList, pageable, productList.size());

        return products;
    }

    @Override
    public Page<Product> getProductSortByPrice(Integer pageNo) {
        Pageable pageable = PageRequest.of(pageNo - 1, 9);
        List<Product> productList = productRepository.getProductListSortByPrice();

        Page<Product> products = new PageImpl<>(productList, pageable, productList.size());

        return products;
    }

    @Override
    public Page<Product> getProductSortByQuantitySold(Integer pageNo) {
        Pageable pageable = PageRequest.of(pageNo - 1, 9);
        List<Product> productList = productRepository.getProductListSortByQuantitySold();

        Page<Product> products = new PageImpl<>(productList, pageable, productList.size());

        return products;
    }

    @Override
    public Page<Product> searchProduct(String name, Integer pageNo) {
        Pageable pageable = PageRequest.of(pageNo - 1, 9);
        List<Product> productList = productRepository.searchProducts(name);

        Page<Product> products = new PageImpl<>(productList, pageable, productList.size());

        return products;
    }

    @Override
    public Product findById(Long id) {
        return productRepository.getById(id);
    }

    @Override
    public Product save(MultipartFile imageProduct, Product product) {
        Product newProduct = new Product();
        try {
            if (imageProduct == null) {
                newProduct.setImage(null);
            } else {
                imageUpload.uploadFile(imageProduct);
                newProduct.setImage(Base64.getEncoder().encodeToString(imageProduct.getBytes()));
            }
            newProduct.setName(product.getName());
            newProduct.setDescription(product.getDescription());
            newProduct.setQuantity(product.getQuantity());
            newProduct.setQuantitySold(0L);
            newProduct.setPrice(product.getPrice());
            newProduct.setCategory(product.getCategory());

            return productRepository.save(newProduct);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Product update(MultipartFile imageProduct, Product product) {
        Product newProduct = productRepository.getById(product.getId());
        try {
            if (imageProduct == null) {
                newProduct.setImage(null);
            } else {
                imageUpload.uploadFile(imageProduct);
                newProduct.setImage(Base64.getEncoder().encodeToString(imageProduct.getBytes()));
            }
            newProduct.setName(product.getName());
            newProduct.setDescription(product.getDescription());
            newProduct.setQuantity(product.getQuantity());
            newProduct.setPrice(product.getPrice());
            newProduct.setCategory(product.getCategory());

            return productRepository.saveAndFlush(newProduct);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public List<Product> getProductByCategoryId(Long id) {
        return productRepository.getProductByCategoryId(id);
    }

    @Override
    public List<Product> getProductByQuantity(Long quantity) {
        return productRepository.getSixProduct(quantity);
    }

    @Override
    public Map<String, Long> getProductByCategory() {
        List<Category> categories = categoryRepository.findAll();
        Map<String, Long> result = new TreeMap<>();

        for(Category category : categories){
            List<Product> products = productRepository.getProductByCategoryId(category.getId());
            result.put(category.getName(), (long) products.size());
        }

        return result;
    }

    @Override
    public Order checkoutProduct(User user, List<Product> products, List<String> quantitys) {
        Order order = new Order();
        order.setUser(user);
        order.setStatus(0L);

        List<OrderDetail> orderDetails = new ArrayList<>();

        for(int i = 0; i < products.size(); ++i){
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrder(order);
            orderDetail.setProduct(products.get(i));
            orderDetail.setQuantity(Long.parseLong(quantitys.get(i)));
            orderDetail.setCreated_at(new Date());
            orderDetail.setTotal(orderDetail.getQuantity() * products.get(i).getPrice());
            orderDetails.add(orderDetail);
        }

        order.setOrderDetails(orderDetails);
        return order;
    }


}
