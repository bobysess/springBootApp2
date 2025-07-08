package com.bobysess.springBootApp2;

import java.math.BigDecimal;

import org.hibernate.internal.TransactionManagement;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final EntityManager entityManager;
    private final ProductRepository productRepository;
    private final TransactionTemplate template;

    @Bean
    ApplicationRunner saveSomeProducts() {
        return args -> {
            var product = new Product(null, "Ananas", BigDecimal.valueOf(10));
            productRepository.save(product);
            product.setName("Banane");
            productRepository.saveAndFlush(product);
            product.setPrice(BigDecimal.valueOf(22));
            productRepository.saveAndFlush(product);
            // productRepository.deleteById(product.getId());
        };
    }

}
