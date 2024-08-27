package com.martin.microservices.product.services.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.martin.microservices.product.TestData;
import com.martin.microservices.product.dto.ProductRequest;
import com.martin.microservices.product.dto.ProductResponse;
import com.martin.microservices.product.models.Product;
import com.martin.microservices.product.repositories.ProductRepository;

@ExtendWith(MockitoExtension.class)
public class ProductServiceImplTest {

        @InjectMocks
        private ProductServiceImpl underTest;

        @Mock
        private ProductRepository productRepository;

        @Test
        public void testThatProductIsCreated() {
                final ProductRequest productRequest = TestData.testProductRequest();
                final Product productToSave = Product.builder()
                                .name(productRequest.name())
                                .description(productRequest.description())
                                .price(productRequest.price())
                                .build();
                final Product savedProduct = TestData.testProductModel();
                final ProductResponse productResponse = TestData.testProductResponse();

                when(productRepository.save(eq(productToSave))).thenReturn(savedProduct);
                final ProductResponse result = underTest.createProduct(productRequest);
                assertEquals(productResponse, result);
        }

        @Test
        public void testThatProductsAreFetched() {
                final ProductResponse productResponse = TestData.testProductResponse();
                final List<ProductResponse> allProductsResponse = new ArrayList<ProductResponse>();
                allProductsResponse.add(productResponse);

                final Product product = TestData.testProductModel();
                final List<Product> allProducts = new ArrayList<Product>();
                allProducts.add(product);

                when(productRepository.findAll()).thenReturn(allProducts);
                final List<ProductResponse> result = underTest.getAllProducts();
                assertEquals(allProductsResponse, result);
        }
}
