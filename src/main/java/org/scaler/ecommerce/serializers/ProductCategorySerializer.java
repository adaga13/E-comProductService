package org.scaler.ecommerce.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.scaler.ecommerce.models.Category;

import java.io.IOException;

public class ProductCategorySerializer extends StdSerializer<Category> {

    public ProductCategorySerializer() {
        this(null);
    }

    public ProductCategorySerializer(Class<Category> t) {
        super(t);
    }

    @Override
    public void serialize(Category category, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject("category");
        if (category.getId() != null)
            jsonGenerator.writeNumberField("id", category.getId());

        if (category.getName() != null)
            jsonGenerator.writeStringField("name", category.getName());

        if (category.getCreatedAt() != null)
            jsonGenerator.writeStringField("createdAt", String.valueOf(category.getCreatedAt()));

        if (category.getLastUpdatedAt() != null)
            jsonGenerator.writeStringField("lastUpdatedAt", String.valueOf(category.getLastUpdatedAt()));

        jsonGenerator.writeEndObject();
    }
}
