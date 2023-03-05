package com.webapp.webapp;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.webapp.webapp.data.models.Product;
import com.webapp.webapp.data.models.User;
import com.webapp.webapp.data.repository.ProductRepository;
import com.webapp.webapp.data.repository.UserRepository;

@ExtendWith(SpringExtension.class)
@SpringBootTest

class ProductRepositoryTest {

    @Autowired
    ProductRepository productRepository;

    static boolean recordsCreated = false;

    @Test
    final void testFindUserByFirstName() {
        Product product = new Product();
        product.setName("test01");
        product.setDescription("test");
        product.setSku("aa");
        product.setManufacturer("test0");
        productRepository.save(product);
        String name = "test01";
        Product product1 = productRepository.findByName(name).get();
        assertNotNull(product1);

        assertTrue(product1.getName().equals(name));
    }

}
