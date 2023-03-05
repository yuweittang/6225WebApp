package com.webapp.webapp.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webapp.webapp.data.models.Product;
import com.webapp.webapp.data.payloads.request.ProductRequest;
import com.webapp.webapp.data.repository.ProductRepository;

@Service
public class ProductServiceImp implements ProductService {
    @Autowired
    ProductRepository productRepository;

    @Override
    public Product createProduct(ProductRequest productRequest) {
        Product product = new Product();
        product.setName(productRequest.getName());
        product.setDescription(productRequest.getDescription());
        product.setSku(productRequest.getSku());
        product.setManufacturer(productRequest.getManufacturer());
        // Product.setQuantity(0);
        productRepository.save(product);
        System.out.println(productRequest.getName());
        System.out.println(product.getName());
        return product;
    }

    @Override
    public Optional<Product> updateProduct(Integer productId, ProductRequest productRequest) {
        Optional<Product> product = productRepository.findById(productId);
        if (product.isEmpty()) {

        }
        product.get().setName(productRequest.getName());
        product.get().setDescription(productRequest.getDescription());
        product.get().setSku(productRequest.getSku());
        product.get().setManufacturer(productRequest.getManufacturer());
        productRepository.save(product.get());
        return product;
    }

    @Override
    public void deleteProduct(Integer productId) {
        Optional<Product> product = productRepository.findById(productId);
        if (product.isEmpty()) {

        }
        productRepository.deleteById(productId);

    }

    @Override
    public Optional<Product> getAProduct(Integer productId) {
        Optional<Product> product = productRepository.findById(productId);
        return product;
    }

}
