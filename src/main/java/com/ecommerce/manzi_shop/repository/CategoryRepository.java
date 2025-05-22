package com.ecommerce.manzi_shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.manzi_shop.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

}
