package org.scaler.ecommerce.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import static com.fasterxml.jackson.annotation.JsonInclude.Include;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Version;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.scaler.ecommerce.serializers.ProductCategorySerializer;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@Builder
@Entity
@JsonInclude(Include.NON_NULL)
public class Product extends BaseModel implements Serializable {

    private String title;

    private String description;

    private Double price;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonSerialize(using = ProductCategorySerializer.class)
    private Category category;

    private String imageUrl;

    private int quantity;

    @Version
    private Integer version = 1;

    public Product() {

    }

    @Override
    public String toString() {
        return "{title = " + this.title + ", description = " + this.description + "}";
    }
}
