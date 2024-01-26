package org.scaler.ecommerce.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import static com.fasterxml.jackson.annotation.JsonInclude.Include;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;
import org.scaler.ecommerce.serializers.ProductSerializer;

import java.util.List;

@Getter
@Setter
@Entity
@JsonInclude(Include.NON_NULL)
public class Category extends BaseModel {

    private String name;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "category")
    @JsonSerialize(using = ProductSerializer.class)
    List<Product> products;

    public Category() {

    }

    public Category(String name) {
        this.name = name;
    }
}
