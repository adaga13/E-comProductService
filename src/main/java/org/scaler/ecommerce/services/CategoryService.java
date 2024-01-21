package org.scaler.ecommerce.services;

import org.scaler.ecommerce.dto.FakeStoreProductDTO;
import org.scaler.ecommerce.models.Category;
import org.scaler.ecommerce.models.Product;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    private final RestTemplate restTemplate;

    public CategoryService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String[] getAllCategories() {
        return restTemplate.getForObject("https://fakestoreapi.com/products/categories", String[].class);
    }

    public List<Product> getCategory(String category) {
        FakeStoreProductDTO[] products = restTemplate.getForObject("https://fakestoreapi.com/products/category/" + category,
                FakeStoreProductDTO[].class);

        if (products == null)
            return new ArrayList<>();

        return Arrays.stream(products)
                .map(this::convertProductDTOToProduct)
                .collect(Collectors.toList());
    }

    private Product convertProductDTOToProduct(FakeStoreProductDTO fakeStoreProductDTO) {
        Product product = new Product();
        product.setId(fakeStoreProductDTO.getId());
        product.setDescription(fakeStoreProductDTO.getDescription());
        product.setTitle(fakeStoreProductDTO.getTitle());
        product.setPrice(fakeStoreProductDTO.getPrice());
        product.setImageUrl(fakeStoreProductDTO.getImage());
        product.setCategory(new Category());
        return product;
    }
}
