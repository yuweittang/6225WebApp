package com.webapp.webapp.data.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.webapp.webapp.data.models.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    Optional<Product> findByName(String name);
}
