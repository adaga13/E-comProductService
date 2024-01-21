package org.scaler.ecommerce.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import static com.fasterxml.jackson.annotation.JsonInclude.Include;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
@Entity
@JsonInclude(Include.NON_NULL)
public class Product extends BaseModel {

    private String title;

    private String description;

    private Double price;

    @ManyToOne(fetch = FetchType.EAGER)
    private Category category;

    private String imageUrl;

    public Product() {

    }

    @Override
    public String toString() {
        return "{title = " + this.title + ", description = " + this.description + "}";
    }
}