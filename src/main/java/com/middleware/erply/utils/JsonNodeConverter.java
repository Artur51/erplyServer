package com.middleware.erply.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import javax.persistence.AttributeConverter;

public class JsonNodeConverter implements AttributeConverter<JsonNode, String> {
    private static final Logger logger = LoggerFactory.getLogger(JsonNodeConverter.class);

    @Override
    public String convertToDatabaseColumn(
            JsonNode jsonNode) {
        if (jsonNode == null) {
            return null;
        }
        return jsonNode.toPrettyString();
    }

    @Override
    public JsonNode convertToEntityAttribute(String jsonNodeString) {
        if (StringUtils.isEmpty(jsonNodeString)) {
            return null;
        }
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readTree(jsonNodeString);
        } catch (JsonProcessingException e) {
            logger.error("Error parsing jsonNodeString", e);
        }
        return null;
    }
}
