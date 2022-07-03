package com.middleware.erply.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class JsonNodeConverterTest {


    @Test
    @DisplayName("convertToDatabaseColumn method should convert value from node")
    public void testConvertToDatabaseColumnMethodShouldConvertValueFromNode() {
      JsonNodeConverter instance = new JsonNodeConverter();

      ObjectMapper mapper = new ObjectMapper();
      ObjectNode node = mapper.createObjectNode();
      node.put("text", "this");
      String result = instance.convertToDatabaseColumn(node);
      assertEquals("{\n"
              + "  \"text\" : \"this\"\n"
              + "}", result.replace("\r", ""));
    }

    @Test
    @DisplayName("convertToEntityAttribute method should convert value from string")
    public void testConvertToEntityAttributeMethodShouldConvertValueFromString() {
      JsonNodeConverter instance = new JsonNodeConverter();
      JsonNode node = instance.convertToEntityAttribute("{\n"
              + "  \"text\" : \"this\"\n"
              + "}");
      assertEquals("{\n"
              + "  \"text\" : \"this\"\n"
              + "}", node.toPrettyString().replace("\r", ""));
    }

}
