package org.scaler.ecommerce.repositories.projections;

public interface ProductProjection {

    Long getId();

    String getTitle();

    String getDescription();

    Double getPrice();

    String getImageUrl();
}
