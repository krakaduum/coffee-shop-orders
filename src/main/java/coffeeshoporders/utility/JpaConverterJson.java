package coffeeshoporders.utility;

import coffeeshoporders.model.order_events.OrderEventState;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.io.IOException;

@Converter
public class JpaConverterJson implements AttributeConverter<Object, String> {

    private final ObjectMapper objectMapper;

    public JpaConverterJson() {
        objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
    }

    @Override
    public String convertToDatabaseColumn(Object meta) {
        try {
            return objectMapper.writeValueAsString(meta);
        } catch (JsonProcessingException ex) {
            return null;
        }
    }

    @Override
    public Object convertToEntityAttribute(String dbData) {
        try {
            return objectMapper.readValue(dbData, OrderEventState.class);
        } catch (IOException ex) {
            return null;
        }
    }

}
