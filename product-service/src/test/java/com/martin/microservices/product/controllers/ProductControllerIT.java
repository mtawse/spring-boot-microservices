package com.martin.microservices.product.controllers;

import java.math.BigDecimal;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.MongoDBContainer;

import com.martin.microservices.product.TestData;
import com.martin.microservices.product.dto.ProductRequest;
import com.martin.microservices.product.dto.ProductResponse;
import com.martin.microservices.product.services.ProductService;

import io.restassured.RestAssured;
import io.restassured.config.JsonConfig;
import io.restassured.http.ContentType;
import io.restassured.path.json.config.JsonPathConfig;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductControllerIT {

    @ServiceConnection
    static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:7.0.5");

    static {
        mongoDBContainer.start();
    }

    @LocalServerPort
    private Integer port;

    @BeforeEach
    void setUp() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;

        JsonConfig jsonConfig = JsonConfig.jsonConfig().numberReturnType(JsonPathConfig.NumberReturnType.DOUBLE);
        RestAssured.config = RestAssured.config().jsonConfig(jsonConfig);
    }

    @Autowired
    private ProductService productService;

    final String productsApiPath = "/api/products";

    @Test
    public void testThatProductIsCreated() {
        final String requestBody = TestData.testProductRequestBody();
        final ProductResponse productResponse = TestData.testProductResponse();
        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post(productsApiPath)
                .then()
                .statusCode(201)
                .body("id", Matchers.notNullValue())
                .body("name", Matchers.equalTo(productResponse.name()))
                .body("description", Matchers.equalTo(productResponse.description()))
                .body("price", Matchers.closeTo(productResponse.price().doubleValue(), 0.01));
    }

    @Test
    public void testThatProductsAreListed() {

        final ProductRequest productRequest = TestData.testProductRequest();
        productService.createProduct(productRequest);

        final ProductResponse productResponse = TestData.testProductResponse();

        RestAssured.given()
                .contentType(ContentType.JSON)
                .when()
                .get(productsApiPath)
                .then()
                .statusCode(200)
                .body("[0].id", Matchers.notNullValue())
                .body("[0].name", Matchers.equalTo(productResponse.name()))
                .body("[0].description", Matchers.equalTo(productResponse.description()))
                .body("[0].price", Matchers.closeTo(productResponse.price().doubleValue(), 0.01));
    }

}
