package com.middleware.erply.clients;

import com.middleware.erply.model.product.ProductGroup;
import com.middleware.erply.model.product.ProductGroupRequest;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Disabled// not unit test, live connection test.
public class ProductsGroupClientTest extends ErplySessionBasedTest {
    @Autowired
    ProductsGroupClient productsGroupClient;

    @Test
    @DisplayName("create method should create and read data")
    // deleted group cannot be recreated.
    // I have deleted group with id 2 and on create there is an issue
    // [ProductsGroupClient#create(ProductGroupRequest)]: [{"message":"group with id 2 was not found"}]
    public void testCreateMethodShouldCreateAndReadData() {
        ProductGroupRequest group = new ProductGroupRequest();
        group.setMainId(ThreadLocalRandom.current().nextInt(1, 6));
        group.setName("en", "group name");
        group.setDescription("en", "test description");

        String result = productsGroupClient.create(group);
        assertNotNull(result);

        List<ProductGroup> data = productsGroupClient.read(0, 20, null, null, null);

        assertNotNull(data);
        assertTrue(1 <= data.size(), "data size is " + data.size() + "; expected value >= 1");
        String ids = data.stream().map(ProductGroup::getId).filter(a -> a != 1).map(a -> a.toString()).collect(Collectors.joining(";"));

        productsGroupClient.delete(ids);
        data = productsGroupClient.read(0, 20, null, null, null);
        assertEquals(1, data.size(), " should be onle one group after delete; size is: " + data.size());
    }
}
