package com.ecommerce.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.Database.Cart;

@Repository
public interface C_Repository extends JpaRepository<Cart, Long> {

}
