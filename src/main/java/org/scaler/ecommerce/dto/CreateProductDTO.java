package org.scaler.ecommerce.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateProductDTO {
    private String title;

    private String description;

    private Double price;

    private String category;

    private String image;
}
