package org.scaler.ecommerce.services;

import org.scaler.ecommerce.exceptions.ProductDoesNotExistException;
import org.scaler.ecommerce.models.Product;

import java.util.List;

public interface ProductService {
    Product getSingleProduct(Long id) throws ProductDoesNotExistException;

    List<Product> getAllProducts();

    Product addProduct(Product product);

    Product updateProduct(Long id, Product product) throws ProductDoesNotExistException;

    Product replaceProduct(Long id, Product product);

    void deleteProduct(Long id) throws ProductDoesNotExistException;

    List<Product> getProductsByCategoryId(Long id);
}
