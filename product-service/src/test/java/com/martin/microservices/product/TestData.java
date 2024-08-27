package com.martin.microservices.product;

import java.math.BigDecimal;

import com.martin.microservices.product.dto.ProductRequest;
import com.martin.microservices.product.dto.ProductResponse;
import com.martin.microservices.product.models.Product;

public final class TestData {

    private TestData() {
    }

    public static ProductRequest testProductRequest() {
        return new ProductRequest("Test Product",
                "Description of a test product", new BigDecimal(9.99));
    }

    public static Product testProductModel() {
        return Product.builder()
                .id("product-id")
                .name(testProductRequest().name())
                .description(testProductRequest().description())
                .price(testProductRequest().price())
                .build();
    }

    public static ProductResponse testProductResponse() {
        return new ProductResponse(testProductModel().getId(), testProductModel().getName(),
                testProductModel().getDescription(), testProductModel().getPrice());

    }

    public static String testProductRequestBody() {
        final String requestBody = """
            {
                "name": "Test Product",
                "description": "Description of a test product",
                "price": 9.99
            }
            """;
        return requestBody;
    }
}
