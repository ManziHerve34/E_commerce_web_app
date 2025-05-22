package com.ecommerce.manzi_shop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.manzi_shop.model.Category;
import com.ecommerce.manzi_shop.repository.CategoryRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    @Autowired
    CategoryRepository categoryRepository;
    public void addCategory(Category category){ categoryRepository.save(category);}
    public List<Category> getAllCategory(){ return categoryRepository.findAll();}
    public void deleteCategoryById(int id){ categoryRepository.deleteById(id);}
    public Optional<Category> getCategoryById(int id){return categoryRepository.findById(id);}

}
