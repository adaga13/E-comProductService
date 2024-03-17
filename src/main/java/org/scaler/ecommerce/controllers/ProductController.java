package org.scaler.ecommerce.controllers;

import org.scaler.ecommerce.dto.BuyProductDto;
import org.scaler.ecommerce.exceptions.ProductDoesNotExistException;
import org.scaler.ecommerce.models.Product;
import org.scaler.ecommerce.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
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
    public Page<Product> getAllProducts(@RequestParam("pageNumber") int pageNumber,
                                        @RequestParam("pageSize") int pageSize,
                                        @RequestParam("sortBy") String sortBy,
                                        @RequestParam("orderBy")Sort.Direction orderBy) {
        return productService.getAllProducts(pageNumber, pageSize, sortBy, orderBy);
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

    @PostMapping("/buy")
    public ResponseEntity<Boolean> buyProduct(@RequestBody BuyProductDto buyProductDto) throws ProductDoesNotExistException {
        return ResponseEntity.ok(productService.buyProduct(buyProductDto.getProductId(), buyProductDto.getQuantity()));

    }
}
