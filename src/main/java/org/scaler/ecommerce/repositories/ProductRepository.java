package org.scaler.ecommerce.repositories;

import org.scaler.ecommerce.models.Category;
import org.scaler.ecommerce.models.Product;
import org.scaler.ecommerce.repositories.projections.ProductProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Page<Product> findAll(Pageable pageable);
    Optional<Product> findById(Long id);
    List<Product> findByCategory(Category category);

    Product findByTitle(String title);

    // Find products given a category id
    List<Product> findByCategory_Id(Long id);

    List<Product> findByPriceBetween(Double startRange, Double endRange);

    @Query("select p.id as id, p.title as title from Product p where p.category.name = ?1 AND p.price BETWEEN ?2 AND ?3")
    List<ProductProjection> findProductsByCategoryAndPrice(String categoryName, Double startPrice, Double endPrice, Pageable pageable);

    @Query("select p from Product p where p.title = :title AND p.price BETWEEN :p1 AND :p2")
    Page<Product> findProductsByTitleAndPrice(@Param("title") String productTitle,
                                              @Param("p1") Double startPrice, @Param("p2") Double endPrice, Pageable pageable);

}
