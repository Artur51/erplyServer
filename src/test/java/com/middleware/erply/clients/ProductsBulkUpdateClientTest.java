package com.middleware.erply.clients;

import com.middleware.erply.clients.ProductsClient;
import com.middleware.erply.clients.ProductsBulkUpdateClient;
import com.middleware.erply.model.ProductDeleteResponse;
import com.middleware.erply.model.product.Product;
import com.middleware.erply.model.product.ProductResponse;
import com.middleware.erply.model.product.bulk.BulkResultData;
import com.middleware.erply.model.product.bulk.BulkUpdateProductRequest;
import com.middleware.erply.services.ProductInstanceGenerationService;
import com.middleware.erply.services.ProductsManipulationService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Disabled// not unit test, live connection test.
public class ProductsBulkUpdateClientTest {

    @Autowired
    ProductsBulkUpdateClient productsBulkUpdateClient;
    @Autowired
    ProductsClient productsClient;
    @Autowired
    ProductsManipulationService productsManipulationService;
    @Autowired
    ProductInstanceGenerationService productInstanceGenerationService;

    @Test
    @DisplayName("create method should create several products")
    public void testCreateMethodShouldCreateSeveralProducts() {
        BulkUpdateProductRequest request = productInstanceGenerationService.generateRequest(Product::new, 3);

        BulkResultData result = productsBulkUpdateClient.create(request);
        assertNotNull(result);

        assertEquals(3, result.getResults().size());
        assertNotNull(result);
        String ids = result.getResults().stream()
                .map(a -> "" + a.getResourceId())
                .collect(Collectors.joining(";"));
        ProductDeleteResponse deleteResult = productsClient.delete(ids);

        assertNotNull(deleteResult);
        assertNull(deleteResult.getMessage());
        assertNotNull(deleteResult.getId());
    }

    @Test
    @DisplayName("update method should update several products")
    public void testUpdateMethodShouldUpdateSeveralProducts() {
        BulkUpdateProductRequest request = productInstanceGenerationService.generateRequest(Product::new, 3);
        BulkResultData result = productsBulkUpdateClient.create(request);
        assertNotNull(result);
        assertEquals(3, result.getResults().size());

        request.getRequests().forEach(productInstanceGenerationService::updateStringFields);

        BulkResultData updateResult = productsBulkUpdateClient.update(request);

        assertNotNull(updateResult);
        assertEquals(3, updateResult.getResults().size());
    }

    @Test
    @DisplayName("get method should return several products")
    public void testGetMethodShouldReturnSeveralProducts() {
        BulkUpdateProductRequest createRequest = productInstanceGenerationService.generateRequest(Product::new, 21);

        BulkResultData createResult = productsBulkUpdateClient.create(createRequest);
        assertNotNull(createResult);
        assertNotNull(createResult.getResults());
        assertEquals(21, createResult.getResults().size());

        String fields = String.join(",", Product.fieldNames);
        List<ProductResponse> readResult = productsClient.read(fields, 0, 20, null, null, false);
        assertNotNull(readResult);
        assertEquals(20, readResult.size());
    }
}
