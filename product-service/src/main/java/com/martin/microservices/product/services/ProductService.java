package com.martin.microservices.product.services;

import java.util.List;

import com.martin.microservices.product.dto.ProductRequest;
import com.martin.microservices.product.dto.ProductResponse;

public interface ProductService {
    ProductResponse createProduct(ProductRequest productRequest);

    List<ProductResponse> getAllProducts();
}
