package com.ecommerce.manzi_shop.global;

import java.util.ArrayList;
import java.util.List;

import com.ecommerce.manzi_shop.model.Product;

public class GlobalData {
    public static List<Product> cart;
    static {
        cart = new ArrayList<Product>();
    }
}
