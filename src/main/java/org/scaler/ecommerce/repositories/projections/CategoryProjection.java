package org.scaler.ecommerce.repositories.projections;

import org.scaler.ecommerce.models.Product;

import java.util.Date;
import java.util.List;

/**
 * Projection for {@link org.scaler.ecommerce.models.Category}
 */
public interface CategoryProjection {

    Long getId();

    Date getCreatedAt();

    Date getLastUpdatedAt();

    String getName();

    List<Product> getProducts();
}