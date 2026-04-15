package com.ecommerce.Controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.Database.Cart;
import com.ecommerce.Database.Products;
import com.ecommerce.Service.C_Service;

@RestController
public class Controller {

    public final C_Service c_service;

    public Controller(C_Service c_service) {
        this.c_service = c_service;
    }

    @PostMapping("/addProducts")
    public Products addProducts(@RequestBody Products products) {
        return c_service.addProducts(products);
    }

    @GetMapping("/AllProducts")
    public List<Products> getAllProducts() {
        return c_service.getAllProducts();
    }

    @GetMapping("/AllProducts/getproduct/{id}")
    public Products getProductsById(@PathVariable Long id) {
        return c_service.getProductsById(id);
    }

    @PostMapping("/AllProducts/getproduct/{id}/add_to_cart")
    public Products addToCart(@PathVariable Long id, @RequestParam(defaultValue = "1") int q) {
        return c_service.FetchFromProduct(id, q);
    }

    @GetMapping("/getproduct/add_to_cart/buynow")
    public Cart calculateTotalPrice(Cart cart) {
        return c_service.calculateTotalPrice(cart);

    }

    @PutMapping("/update_product/{id}")
    public Products updateProduct(@PathVariable Long id, @RequestBody Products updatedProduct, @RequestParam int cr) {
        return c_service.updateProduct(id, updatedProduct, cr);
    }

    @DeleteMapping("/cancel_order")
    public void delete() {
        c_service.delete();
    }

    @GetMapping("/price_range")
    public List<Products> ProductRange(@RequestParam double min, @RequestParam double max) {
        return c_service.ProductRange(min, max);
    }

    @DeleteMapping("restart_db")
    public void restartDB() {
        c_service.restartDB();
    }

}
