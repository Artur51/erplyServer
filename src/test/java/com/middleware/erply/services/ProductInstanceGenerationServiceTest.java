package com.middleware.erply.services;

import com.middleware.erply.model.product.Product;
import com.middleware.erply.model.product.bulk.BulkUpdateProductRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ProductInstanceGenerationServiceTest {
    ProductInstanceGenerationService productInstanceGenerationService = new ProductInstanceGenerationService();

    @Test
    @DisplayName("generateRequest method generates request with instances")
    public void testGenerateRequestMethodGeneratesRequestWithInstances() {
        BulkUpdateProductRequest result = productInstanceGenerationService.generateRequest(Product::new, 10);
        assertNotNull(result);
        assertNotNull(result.getRequests());
        assertEquals(10, result.getRequests().size());
        assertEquals(1, result.getRequests().get(0).getGroupId());
    }

    @Test
    @DisplayName("generateProduct method generates class instance")
    public void testGenerateProductMethodGeneratesClassInstance() {
        Product instance = productInstanceGenerationService.generateProduct(Product::new);
        assertNotNull(instance);
        assertEquals(1, instance.getGroupId());
    }

    @Test
    @DisplayName("updateStringFields method should update field value")
    public void testUpdateStringFieldsMethodShouldUpdateFieldValue() {
        Product product = new Product();
        product.setBackupId(123);

        productInstanceGenerationService.updateStringFields(product);
        assertNotNull(product.getAlcoholPercentage());
        assertNotNull(product.getCode());
        assertNotNull(product.getCode2());
        assertEquals(123, product.getBackupId());
    }

    @Test
    @DisplayName("initFields method should setup fields")
    public void testInitFieldsMethodShouldSetupFields() {
        Product product = new Product();

        productInstanceGenerationService.initFields(product);
        assertNotNull(product.getAlcoholPercentage());
        assertNotNull(product.getCode());
        assertNotNull(product.getCode2());
        assertEquals(0, product.getBackupId());
        assertFalse(product.getLabelsNotNeeded());
    }

}
