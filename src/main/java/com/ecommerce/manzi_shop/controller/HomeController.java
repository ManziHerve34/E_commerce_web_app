package com.ecommerce.manzi_shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.ecommerce.manzi_shop.error.ExceptionDetails;
import com.ecommerce.manzi_shop.error.RequestException;
import com.ecommerce.manzi_shop.global.GlobalData;
import com.ecommerce.manzi_shop.service.CategoryService;
import com.ecommerce.manzi_shop.service.ProductService;

import java.time.ZonedDateTime;

@Controller
public class HomeController {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ProductService productService;

    @GetMapping({ "/", "/home" })
    public String home(Model model) {
        model.addAttribute("cartCount", GlobalData.cart.size());
        return "index";
    }

    @GetMapping("/shop")
    public String shop(Model model) {
        model.addAttribute("categories", categoryService.getAllCategory());
        model.addAttribute("products", productService.getAllProduct());
        model.addAttribute("cartCount", GlobalData.cart.size());
        return "shop";
    }

    @GetMapping("/shop/category/{id}")
    public String shopByCategory(Model model, @PathVariable int id) {
        var products = productService.getAllProductByCategoryId(id);
        if (products.isEmpty()) {
            throw new RequestException("No products found for category id: " + id);
        }
        model.addAttribute("categories", categoryService.getAllCategory());
        model.addAttribute("products", products);
        return "shop";
    }

    @GetMapping("/shop/viewproduct/{id}")
    public String viewProduct(Model model, @PathVariable int id) {
        var product = productService.getProductById(id)
                .orElseThrow(() -> new RequestException("Product not found with id: " + id));
        model.addAttribute("product", product);
        model.addAttribute("cartCount", GlobalData.cart.size());
        return "viewProduct";
    }

    @ExceptionHandler(RequestException.class)
    public String handleRequestException(RequestException e, Model model) {
        model.addAttribute("exceptionDetails", new ExceptionDetails(
                e.getMessage(), HttpStatus.BAD_REQUEST, ZonedDateTime.now()));
        return "error";
    }

    @ExceptionHandler(Exception.class)
    public String handleGenericException(Exception e, Model model) {
        model.addAttribute("errorTitle", "Unexpected Error");
        model.addAttribute("errorMessage", "An unexpected error occurred. Please try again later.");
        model.addAttribute("timestamp", ZonedDateTime.now());
        return "error";
    }
}
