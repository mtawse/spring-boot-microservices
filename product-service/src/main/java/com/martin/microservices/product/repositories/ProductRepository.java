package com.martin.microservices.product.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.martin.microservices.product.models.Product;

@Repository
public interface ProductRepository extends MongoRepository<Product, String> {

}
