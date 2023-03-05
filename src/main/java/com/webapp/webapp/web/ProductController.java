package com.webapp.webapp.web;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.webapp.webapp.data.models.Product;
import com.webapp.webapp.data.payloads.request.ProductRequest;
import com.webapp.webapp.service.ProductService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.modelmapper.ModelMapper;

@RestController
@RequestMapping("/v1")
public class ProductController {

    @Autowired
    ProductService productService;

    @PostMapping("/product")
    public ResponseEntity<Product> addProduct(@RequestBody ProductRequest productRequest) {
        ModelMapper mapper = new ModelMapper();
        Product product = new Product();

        return new ResponseEntity<>(product, HttpStatus.CREATED);
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable Integer id) {
        Product product = productService.getAProduct(id).get();
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @PutMapping("/product/{id}")
    public ResponseEntity<Product> updateProductPut(@PathVariable Integer id,
            @RequestBody ProductRequest productRequest) {
        Optional<Product> product = productService.updateProduct(id, productRequest);
        return new ResponseEntity<>(product.get(), HttpStatus.ACCEPTED);
    }

    @PatchMapping("/product/{id}")
    public ResponseEntity<Product> updateProductPatch(@PathVariable Integer id,
            @RequestBody ProductRequest productRequest) {
        Product product = productService.updateProduct(id, productRequest).get();
        return new ResponseEntity<>(product, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/product/{id}")
    public ResponseEntity<Product> deleteProduct(@PathVariable Integer id) {
        productService.deleteProduct(id);
        ;
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
