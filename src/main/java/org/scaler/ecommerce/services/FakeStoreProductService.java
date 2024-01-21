package org.scaler.ecommerce.services;

import org.scaler.ecommerce.dto.CreateProductDTO;
import org.scaler.ecommerce.dto.FakeStoreProductDTO;
import org.scaler.ecommerce.exceptions.ProductDoesNotExistException;
import org.scaler.ecommerce.models.Category;
import org.scaler.ecommerce.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service("fakeStoreProduct")
public class FakeStoreProductService implements ProductService {

    private final RestTemplate restTemplate;

    @Autowired
    public FakeStoreProductService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public Product getSingleProduct(Long id) throws ProductDoesNotExistException {
        FakeStoreProductDTO fakeStoreProductDTO = restTemplate.getForObject("https://fakestoreapi.com/products/" + id,
                FakeStoreProductDTO.class);

        if (fakeStoreProductDTO == null)
            throw new ProductDoesNotExistException("Product with id : " + id + " does not exist.");
        return convertProductDTOToProduct(fakeStoreProductDTO);
    }

    @Override
    public List<Product> getAllProducts() {
        FakeStoreProductDTO[] fakeStoreProductDTOS = restTemplate.getForObject("https://fakestoreapi.com/products",
                FakeStoreProductDTO[].class);
        if (fakeStoreProductDTOS == null)
            return new ArrayList<>();

        return Arrays.stream(fakeStoreProductDTOS)
                .map(this::convertProductDTOToProduct)
                .collect(Collectors.toList());
    }

    @Override
    public Product addProduct(Product product) {
        HttpEntity<FakeStoreProductDTO> request = new HttpEntity<>(convertProductToProductDTO(product));
        FakeStoreProductDTO fakeStoreProductDTO = restTemplate.postForObject("https://fakestoreapi.com/products",
                request, FakeStoreProductDTO.class);
        return convertProductDTOToProduct(fakeStoreProductDTO);
    }

    @Override
    public Product updateProduct(Long id, Product product) {
        FakeStoreProductDTO productDTO = new FakeStoreProductDTO();
        if (product.getTitle() != null)
            productDTO.setTitle(product.getTitle());

        if (product.getPrice() != null)
            productDTO.setPrice(product.getPrice());

        if (product.getCategory() != null)
            productDTO.setCategory(product.getCategory().getName());

        if (product.getImageUrl() != null)
            productDTO.setImage(product.getImageUrl());

        if (product.getDescription() != null)
            productDTO.setDescription(product.getDescription());

        HttpEntity<FakeStoreProductDTO> request = new HttpEntity<>(productDTO);
        FakeStoreProductDTO fakeStoreProductDTO = restTemplate.patchForObject("https://fakestoreapi.com/products/" + id,
                request, FakeStoreProductDTO.class);
        return convertProductDTOToProduct(fakeStoreProductDTO);
    }

    @Override
    public Product replaceProduct(Long id, Product product) {
        HttpEntity<FakeStoreProductDTO> request = new HttpEntity<>(convertProductToProductDTO(product));
        ResponseEntity<FakeStoreProductDTO> responseEntity = restTemplate.exchange("https://fakestoreapi.com/products/" + id,
                HttpMethod.PUT, request, FakeStoreProductDTO.class);
        return convertProductDTOToProduct(responseEntity.getBody());
    }

    @Override
    public void deleteProduct(Long id) {
        restTemplate.delete("https://fakestoreapi.com/products/" + id);
    }

    @Override
    public List<Product> getProductsByCategoryId(Long id) {
        return null;
    }

    private Product convertProductDTOToProduct(FakeStoreProductDTO fakeStoreProductDTO) {
        Product product = new Product();
        product.setId(fakeStoreProductDTO.getId());
        product.setDescription(fakeStoreProductDTO.getDescription());
        product.setTitle(fakeStoreProductDTO.getTitle());
        product.setPrice(fakeStoreProductDTO.getPrice());
        product.setImageUrl(fakeStoreProductDTO.getImage());
        product.setCategory(new Category(fakeStoreProductDTO.getCategory()));
        return product;
    }

    private FakeStoreProductDTO convertProductToProductDTO(Product product) {
        FakeStoreProductDTO productDTO = new FakeStoreProductDTO();
        productDTO.setTitle(product.getTitle());
        productDTO.setPrice(product.getPrice());
        productDTO.setCategory(product.getCategory().getName());
        productDTO.setImage(product.getImageUrl());
        productDTO.setDescription(product.getDescription());
        return productDTO;
    }
}
