package com.ecommerce.computer.repository;

import com.ecommerce.computer.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT p FROM Product p WHERE p.name LIKE %?1% OR p.description like %?1%")
    List<Product> findAllByNameOrDescription(String keyword);

    @Query(value = "select p from Product p where p.category.id = ?1")
    List<Product> getProductByCategoryId(Long id);

    @Query(value = "select p from Product p where p.category.name = ?1")
    List<Product> getProductByCategoryName(String categoryName);

    @Query("SELECT p FROM Product p WHERE p.name LIKE %?1% OR p.description LIKE %?1%")
    List<Product> searchProducts(String keyword);

    @Query("SELECT p FROM Product p ORDER BY p.id DESC LIMIT ?1")
    List<Product> getSixProduct(Long quantity);

    @Query("SELECT p FROM Product p ORDER BY p.created_at DESC")
    List<Product> getProductListSortByLatest();

    @Query("SELECT p FROM Product p ORDER BY p.price DESC")
    List<Product> getProductListSortByPrice();

    @Query("SELECT p FROM Product p ORDER BY p.quantitySold DESC")
    List<Product> getProductListSortByQuantitySold();

}
