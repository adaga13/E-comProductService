package org.scaler.ecommerce.services.impl;

import org.scaler.ecommerce.exceptions.ProductDoesNotExistException;
import org.scaler.ecommerce.models.Category;
import org.scaler.ecommerce.models.Product;
import org.scaler.ecommerce.repositories.CategoryRepository;
import org.scaler.ecommerce.repositories.ProductRepository;
import org.scaler.ecommerce.services.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("selfProduct")
public class SelfProductService implements ProductService {

    private final ProductRepository productRepository;

    private final CategoryRepository categoryRepository;

    public SelfProductService(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Product getSingleProduct(Long id) throws ProductDoesNotExistException {
        return productRepository.findById(id).orElseThrow(() ->
                new ProductDoesNotExistException("Product with id : " + id + " does not exist."));
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product addProduct(Product product) {
        String categoryName = product.getCategory().getName();
        Category category = categoryRepository.findByName(categoryName)
                .orElseGet(() -> categoryRepository.save(new Category(categoryName)));
        product.setCategory(category);
        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(Long id, Product product) throws ProductDoesNotExistException {
        Product existingProduct = productRepository.findById(id).orElseThrow(() ->
                new ProductDoesNotExistException("Product with id : " + id + " does not exist."));

        if (product.getTitle() != null)
            existingProduct.setTitle(product.getTitle());

        if (product.getPrice() != null)
            existingProduct.setPrice(product.getPrice());

        Category category = product.getCategory();
        if (category != null && category.getId() != null) {
            existingProduct.setCategory(category);
        }

        if (product.getImageUrl() != null)
            existingProduct.setImageUrl(product.getImageUrl());

        if (product.getDescription() != null)
            existingProduct.setDescription(product.getDescription());

        return productRepository.save(existingProduct);
    }

    @Override
    public Product replaceProduct(Long id, Product product) {
        return productRepository.save(product);
    }

    @Override
    public void deleteProduct(Long id) throws ProductDoesNotExistException {
        if (productRepository.existsById(id))
            productRepository.deleteById(id);
        else
            throw new ProductDoesNotExistException("Product with id : " + id + " does not exist.");
    }

    @Override
    public List<Product> getProductsByCategoryId(Long id) {
        return productRepository.findByCategory_Id(id);
    }
}
