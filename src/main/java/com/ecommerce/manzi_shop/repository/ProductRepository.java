package com.ecommerce.manzi_shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.manzi_shop.model.Product;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findAllByCategoryId(int id);

}
