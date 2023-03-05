package com.webapp.webapp.service;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.webapp.webapp.data.models.Product;
import com.webapp.webapp.data.payloads.request.ProductRequest;

@Component
public interface ProductService {
    Product createProduct(ProductRequest productRequest);

    Optional<Product> updateProduct(Integer productId, ProductRequest productRequest);

    Optional<Product> getAProduct(Integer productId);

    void deleteProduct(Integer productId);
}
