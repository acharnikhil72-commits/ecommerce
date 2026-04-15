package com.ecommerce.Service;

import java.util.List;
import java.util.stream.Collectors;

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

    public Products FetchFromProduct(Long id, int q) {
        Products pro = p_repository.findById(id).orElse(null);

        if (pro == null) {
            return null;
        }

        addToCart(pro, q);

        int crtquantity = Integer.parseInt(pro.getP_quantity());
        if (crtquantity > 0) {
            pro.setP_quantity(String.valueOf(crtquantity - q));
            p_repository.save(pro);
        }
        return pro;
    }

    public Cart addToCart(Products pro, int q) {
        int qt = Integer.parseInt(pro.getP_quantity());
        if (qt > 0) {

            Cart cart = new Cart();
            cart.setP_name(pro.getP_name());
            cart.setP_price(pro.getP_price());

            int i = 0;
            cart.setP_quantity(String.valueOf(i + q));

            cart.setTotalPrice(pro.getP_price() * q);

            return c_repository.save(cart);
        } else {
            System.out.println("Product is out of stock");
            return null;
        }
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

    public void delete() {
        c_repository.deleteAll();
    }

    public Products updateProduct(Long id, Products p, int cr) {
        Products existing = p_repository.findById(id).orElse(null);
        if (existing == null) {
            return null;
        } else {
            int cc = Integer.parseInt(existing.getP_quantity());

            existing.setP_quantity(String.valueOf(cc + cr));
            existing.setP_name(p.getP_name());
            existing.setP_price(p.getP_price());
            p_repository.save(existing);
            return existing;
        }

    }

    public List<Products> ProductRange(double min, double max) {
        return p_repository.findAll().stream()
                .filter(p -> p.getP_price() >= min && p.getP_price() <= max)
                .collect(Collectors.toList());
    }

    public void restartDB() {
        p_repository.deleteAll();
    }

}
