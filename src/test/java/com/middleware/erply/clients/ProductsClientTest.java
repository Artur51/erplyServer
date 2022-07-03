package com.middleware.erply.clients;

import com.middleware.erply.clients.ProductsClient;
import com.middleware.erply.model.product.Product;
import com.middleware.erply.model.product.ProductResponse;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@Disabled// not unit test, live connection test.
public class ProductsClientTest extends ErplySessionBasedTest {

    @Autowired
    ProductsClient productsClient;

    @Test
    @DisplayName("read method should write and read some data")
    public void testReadMethodShouldWriteAndReadSomeData() {

        Product product = new Product();
        product.setAgeRestriction(20);

        product.setName("en", "test name");
        product.setCode("" + ThreadLocalRandom.current().nextInt(1000, 999999));
        product.setGroupId(1);// default one

        String result = productsClient.create(product);
        assertNotNull(result);

        List<ProductResponse> data = productsClient.read("age_restriction", 0, 100, null, null, true);
        assertNotNull(data);
    }

}
