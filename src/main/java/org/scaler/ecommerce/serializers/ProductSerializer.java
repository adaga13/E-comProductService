package org.scaler.ecommerce.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.scaler.ecommerce.models.Product;

import java.io.IOException;
import java.util.List;

public class ProductSerializer extends StdSerializer<List<Product>> {
    public ProductSerializer() {
        this(null);
    }

    public ProductSerializer(Class<List<Product>> t) {
        super(t);
    }

    @Override
    public void serialize(List<Product> products, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartArray("products");
        for (Product product : products) {
            jsonGenerator.writeStartObject();
            jsonGenerator.writeNumberField("id", product.getId());
            jsonGenerator.writeStringField("title", product.getTitle());
            jsonGenerator.writeNumberField("price", product.getPrice());
            jsonGenerator.writeEndObject();
        }
        jsonGenerator.writeEndArray();
    }
}
