package org.scaler.ecommerce;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.scaler.ecommerce.models.Category;
import org.scaler.ecommerce.repositories.CategoryRepository;
import org.scaler.ecommerce.repositories.ProductRepository;
import org.scaler.ecommerce.repositories.projections.ProductProjection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;

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

    @Test
    void contextLoads() {
    }

    @Test
    void testCategoryFetch() {
        Optional<Category> category = categoryRepository.findByName("Electronic");
        category.get().getProducts();
        Assertions.assertNotNull(categoryRepository.findByName("Electronic").orElseGet(() -> new Category("xyz")));
    }

    @Test
    void testProductsFetchByCategoryName() {
        List<ProductProjection> productProjections = productRepository.findProductsByCategoryAndPrice("Electronic", 1000D, 3000D,
                PageRequest.of(0, 10));

        for (ProductProjection projection : productProjections) {
            System.out.println("Id = " + projection.getId() + " & title = " + projection.getTitle());
        }
    }

    @Test
    void testProductsFetchByTitle() {
        System.out.println(productRepository.findProductsByTitleAndPrice("Mixer Grinder", 1000D, 3000D,
                PageRequest.of(0, 10)).getContent());
    }

}
