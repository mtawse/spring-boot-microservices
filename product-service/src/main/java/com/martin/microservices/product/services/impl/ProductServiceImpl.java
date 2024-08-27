package com.martin.microservices.product.services.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.martin.microservices.product.dto.ProductRequest;
import com.martin.microservices.product.dto.ProductResponse;
import com.martin.microservices.product.models.Product;
import com.martin.microservices.product.repositories.ProductRepository;
import com.martin.microservices.product.services.ProductService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {
        private final ProductRepository productRepository;

        public ProductResponse createProduct(ProductRequest productRequest) {
                final Product product = Product.builder()
                                .name(productRequest.name())
                                .description(productRequest.description())
                                .price(productRequest.price())
                                .build();
                final Product savedProduct = productRepository.save(product);
                log.info("Product created successfully");
                return new ProductResponse(savedProduct.getId(), savedProduct.getName(), savedProduct.getDescription(),
                                savedProduct.getPrice());
        }

        @Override
        public List<ProductResponse> getAllProducts() {
                return productRepository.findAll()
                                .stream()
                                .map(product -> new ProductResponse(product.getId(), product.getName(),
                                                product.getDescription(),
                                                product.getPrice()))
                                .toList();
        }
}
