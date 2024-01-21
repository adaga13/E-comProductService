package org.scaler.ecommerce.controllers;

import org.scaler.ecommerce.dto.CreateProductDTO;
import org.scaler.ecommerce.exceptions.CategoryDoesNotExistException;
import org.scaler.ecommerce.exceptions.ProductDoesNotExistException;
import org.scaler.ecommerce.models.Product;
import org.scaler.ecommerce.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(@Qualifier("selfProduct") ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public Product getProduct(@PathVariable("id") Long id) throws ProductDoesNotExistException {
        return productService.getSingleProduct(id);
    }

    @GetMapping("/categories/{categoryId}")
    public List<Product> getProductsByCategoryId(@PathVariable("categoryId") Long categoryId) {
        return productService.getProductsByCategoryId(categoryId);
    }

    @PostMapping("")
    public Product addProduct(@RequestBody Product product) {
        return productService.addProduct(product);
    }

    // Performs partial update
    @PatchMapping("/{id}")
    public Product updateProduct(@PathVariable("id") Long id, @RequestBody Product product) throws ProductDoesNotExistException {
        return productService.updateProduct(id, product);
    }

    // Replace a product
    @PutMapping("/{id}")
    public Product replaceProduct(@PathVariable("id") Long id, @RequestBody Product product) {
        return productService.replaceProduct(id, product);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable("id") Long id) throws ProductDoesNotExistException {
        productService.deleteProduct(id);
    }

}
