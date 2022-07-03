package com.middleware.erply.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class JsonNodeUtils {

    public static String getTextValue(
            JsonNode node,
            String tag) {
        if (node == null) {
            return null;
        }
        JsonNode jsonNode = node.get(tag);
        return jsonNode == null ? null : jsonNode.textValue();
    }

    public static ObjectNode createNode(
            String tag,
            String value) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode node = mapper.createObjectNode();
        node.put(tag, value);
        return node;
    }

}
