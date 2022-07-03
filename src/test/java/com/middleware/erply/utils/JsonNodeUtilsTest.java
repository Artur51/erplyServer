package com.middleware.erply.utils;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonNodeUtilsTest {

    @Test
    @DisplayName("methods should create node with data and retrieve data from")
    public void testMethodsShouldCreateNodeWithDataAndRetrieveDataFrom() {
        ObjectNode node = JsonNodeUtils.createNode("lang", "en");
        String result = JsonNodeUtils.getTextValue(node, "lang");
        assertEquals("en", result);
    }
}
