package org.scaler.ecommerce.services;

import org.scaler.ecommerce.exceptions.ProductDoesNotExistException;
import org.scaler.ecommerce.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface ProductService {
    Product getSingleProduct(Long id) throws ProductDoesNotExistException;

    Page<Product> getAllProducts(int pageNumber, int pageSize, String sortBy, Sort.Direction orderBy);

    Product addProduct(Product product);

    Product updateProduct(Long id, Product product) throws ProductDoesNotExistException;

    Product replaceProduct(Long id, Product product);

    void deleteProduct(Long id) throws ProductDoesNotExistException;

    List<Product> getProductsByCategoryId(Long id);

    default boolean buyProduct(long productId, int quantity) throws ProductDoesNotExistException {
        return true;
    }
}
