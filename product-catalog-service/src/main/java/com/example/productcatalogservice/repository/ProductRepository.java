package com.example.productcatalogservice.repository;

import com.example.productcatalogservice.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}