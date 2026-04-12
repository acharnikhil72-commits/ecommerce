package com.ecommerce.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ecommerce.Database.Cart;
import com.ecommerce.Database.Products;
import com.ecommerce.Repository.C_Repository;
import com.ecommerce.Repository.P_Repository;

@Service

public class C_Service {

    public final C_Repository c_repository;
    public final P_Repository p_repository;

    public C_Service(C_Repository c_repository, P_Repository p_repository) {
        this.c_repository = c_repository;
        this.p_repository = p_repository;
    }

    public Products addProducts(Products products) {
        return p_repository.save(products);
    }

    public Products getProductsById(Long id) {
        return p_repository.findById(id).orElse(null);
    }

    public Products FetchFromProduct(Long id) {
        Products pro = p_repository.findById(id).orElse(null);

        if (pro == null) {
            return null;
        }

        addToCart(pro);

        int crtquantity = Integer.parseInt(pro.getP_quantity());
        if (crtquantity > 0) {
            pro.setP_quantity(String.valueOf(crtquantity - 1));
            p_repository.save(pro);
        }
        return pro;
    }

    public Cart addToCart(Products pro) {
        Cart cart = new Cart();
        cart.setP_name(pro.getP_name());
        cart.setP_price(pro.getP_price());
        cart.setP_quantity(pro.getP_quantity());

        return c_repository.save(cart);
    }

    public Cart calculateTotalPrice(Cart cart) {
        if (cart == null)
            return null;
        Long unitPrice = cart.getP_price();
        int quantity = Integer.parseInt(cart.getP_quantity());

        Long totalPrice = unitPrice * quantity;
        cart.setTotalPrice(totalPrice);

        c_repository.delete(cart);
        return cart;
    }

    public Cart getCartById(Long id) {
        return c_repository.findById(id).orElse(null);
    }

    public List<Products> getAllProducts() {
        return p_repository.findAll();
    }

}
