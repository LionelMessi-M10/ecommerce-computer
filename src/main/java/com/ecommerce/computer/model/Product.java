package com.ecommerce.computer.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @Column(name = "image", columnDefinition = "MEDIUMBLOB")
    private String image;

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private Double price;

    @Column(name = "quantity")
    private Long quantity;

    @Column(name = "quantity_sold")
    private Long quantitySold;

    @Column(name = "description")
    private String description;

    @Column(name = "created_at")
    private Date created_at;

    @OneToMany(mappedBy = "product")
    List<CartItem> cartItems = new ArrayList<>();

    @OneToMany(mappedBy = "product")
    List<OrderDetail> orderDetails = new ArrayList<>();

    @OneToMany(mappedBy = "product")
    List<BillDetail> billDetails = new ArrayList<>();

    @OneToMany(mappedBy = "product")
    List<SupplyOrder> supplyOrders = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
}
