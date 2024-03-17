package org.scaler.ecommerce.services.impl;

import jakarta.persistence.OptimisticLockException;
import org.scaler.ecommerce.exceptions.ProductDoesNotExistException;
import org.scaler.ecommerce.models.Category;
import org.scaler.ecommerce.models.Product;
import org.scaler.ecommerce.repositories.CategoryRepository;
import org.scaler.ecommerce.repositories.ProductRepository;
import org.scaler.ecommerce.services.ProductService;
import org.slf4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.function.Function;

@Service("selfProduct")
public class SelfProductService implements ProductService {

    private final ProductRepository productRepository;

    private final CategoryRepository categoryRepository;

    private final Logger logger;

    public SelfProductService(ProductRepository productRepository, CategoryRepository categoryRepository, Logger logger) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.logger = logger;
    }

    @Override
    public Product getSingleProduct(Long id) throws ProductDoesNotExistException {
        return productRepository.findById(id).orElseThrow(() ->
                throwProductDoesNotExistException.apply(id));
    }

    @Override
    public Page<Product> getAllProducts(int pageNumber, int pageSize, String sortBy, Sort.Direction orderBy) {
        return productRepository.findAll(PageRequest.of(pageNumber, pageSize, Sort.by(orderBy, sortBy)));
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
                throwProductDoesNotExistException.apply(id));

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
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
            if (logger.isDebugEnabled())
                logger.debug("Product with id : {} deleted successfully.", id);
        }
        else {
            if (logger.isDebugEnabled())
                logger.debug("Product with id : {} doesn't exist.", id);
            throw throwProductDoesNotExistException.apply(id);
        }

    }

    @Override
    public List<Product> getProductsByCategoryId(Long id) {
        return productRepository.findByCategory_Id(id);
    }

    @Override
    @Transactional
    @Retryable(retryFor = { OptimisticLockException.class, ObjectOptimisticLockingFailureException.class }, maxAttempts = 3, backoff = @Backoff(delay = 2500))
    public boolean buyProduct(long productId, int quantity) throws ProductDoesNotExistException {
        if (logger.isDebugEnabled())
            logger.debug("Received request to buy product id :{} for quantity : {}.", productId, quantity);
        Product existingProduct = productRepository.findById(productId).orElseThrow(() ->
                throwProductDoesNotExistException.apply(productId));
        if (existingProduct.getQuantity() < quantity) {
            if (logger.isDebugEnabled())
                logger.debug("Quantity to be bought is greater than available quantity for product id : {}.", productId);
            return false;
        }

        existingProduct.setQuantity(existingProduct.getQuantity() - quantity);
        try {
            productRepository.save(existingProduct);
            if (logger.isDebugEnabled())
                logger.debug("Product with id : {} and quantity : {} bought successfully.", productId, quantity);
            return true;
        } catch (OptimisticLockException | ObjectOptimisticLockingFailureException exception) {
            logger.error("Unable to acquire lock after 3 attempts for buying product id : {}.", productId, exception);
            return false;
        }
    }

//    @Override
//    @Transactional(isolation = Isolation.REPEATABLE_READ)
//    public void buyProduct(long productId, int quantity) throws ProductDoesNotExistException {
//        if (logger.isDebugEnabled())
//            logger.debug("Received request to buy product id :{} for quantity : {}.", productId, quantity);
//        Product existingProduct = productRepository.findById(productId).orElseThrow(() ->
//                throwProductDoesNotExistException.apply(productId));
//        if (existingProduct.getQuantity() < quantity) {
//            if (logger.isDebugEnabled())
//                logger.debug("Quantity to be bought is greater than available quantity for product id : {}.", productId);
//            throw new RuntimeException();
//        }
//
//        existingProduct.setQuantity(existingProduct.getQuantity() - quantity);
//        try {
//            if (quantity == 6)
//                Thread.sleep(4000);
//            productRepository.save(existingProduct);
//            if (logger.isDebugEnabled())
//                logger.debug("Product with id : {} and quantity : {} bought successfully.", productId, quantity);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
//    }

    Function<Long, ProductDoesNotExistException> throwProductDoesNotExistException =
            (productId) -> new ProductDoesNotExistException("Product with id : " + productId + " does not exist.");
}
