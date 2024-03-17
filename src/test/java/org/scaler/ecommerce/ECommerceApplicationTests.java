package org.scaler.ecommerce;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.scaler.ecommerce.models.Category;
import org.scaler.ecommerce.models.Product;
import org.scaler.ecommerce.repositories.CategoryRepository;
import org.scaler.ecommerce.repositories.ProductRepository;
import org.scaler.ecommerce.repositories.projections.ProductProjection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootTest
class ECommerceApplicationTests {

    private final CategoryRepository categoryRepository;

    private final ProductRepository productRepository;

    @Autowired
    ECommerceApplicationTests(CategoryRepository categoryRepository, ProductRepository productRepository) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
    }

//    @Test
//    void contextLoads() {
//    }

//    @Test
//    @Transactional
//    void testCategoryFetch() throws JsonProcessingException {
//        List<Category> categories = categoryRepository.findAll();
//        System.out.println(new ObjectMapper().writeValueAsString(categories));
//        Assertions.assertNotNull(categoryRepository.findByName("Mobile").orElseGet(() -> new Category("xyz")));
//    }
//
//    @Test
//    void testProductsFetchByCategoryName() {
//        List<ProductProjection> productProjections = productRepository.findProductsByCategoryAndPrice("Electronic", 1000D, 3000D,
//                PageRequest.of(0, 10));
//
//        for (ProductProjection projection : productProjections) {
//            System.out.println("Id = " + projection.getId() + " & title = " + projection.getTitle());
//        }
//    }
//
//    @Test
//    void testProductsFetchByTitle() {
//        System.out.println(productRepository.findProductsByTitleAndPrice("Mixer Grinder", 1000D, 3000D,
//                PageRequest.of(0, 10)).getContent());
//    }
//
//    @Test
//    @Transactional
//    void testProductsFetch() throws JsonProcessingException {
//        List<Product> products = productRepository.findAll();
//        System.out.println(new ObjectMapper().writeValueAsString(products));
//    }

}
