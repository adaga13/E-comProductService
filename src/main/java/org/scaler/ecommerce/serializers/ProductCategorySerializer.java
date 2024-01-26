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
        jsonGenerator.writeNumberField("id", category.getId());
        jsonGenerator.writeStringField("name", category.getName());
        jsonGenerator.writeStringField("createdAt", String.valueOf(category.getCreatedAt()));
        jsonGenerator.writeStringField("lastUpdatedAt", String.valueOf(category.getLastUpdatedAt()));
        jsonGenerator.writeEndObject();
    }
}
