package com.ecommerce.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.Database.Products;

@Repository
public interface P_Repository extends JpaRepository<Products, Long> {

}
