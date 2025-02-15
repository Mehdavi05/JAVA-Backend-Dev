package dev.shujaat.springboot_search_rest_api.services;

import dev.shujaat.springboot_search_rest_api.entities.Product;

import java.util.List;

public interface IProductService {
    List<Product> searchProducts(String query);
    Product createProduct(Product product);
}
