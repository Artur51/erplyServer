package com.middleware.erply.services;

import com.middleware.erply.clients.ProductsBulkUpdateClient;
import com.middleware.erply.clients.ProductsClient;
import com.middleware.erply.model.ProductDeleteResponse;
import com.middleware.erply.model.product.Product;
import com.middleware.erply.model.product.ProductResponse;
import com.middleware.erply.model.product.bulk.BulkResult;
import com.middleware.erply.model.product.bulk.BulkResultData;
import com.middleware.erply.model.product.bulk.BulkUpdateProductRequest;
import com.middleware.erply.model.view.EntryIdView;
import com.middleware.erply.repositories.ProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class ProductsManipulationServiceTest {
    @Autowired
    ProductsManipulationService productsManipulationService;
    @MockBean
    ProductRepository productRepository;
    @MockBean
    ProductsClient productsClient;
    @MockBean
    ProductsBulkUpdateClient productsBulkUpdateClient;

    @Test
    @DisplayName("deleteAll method deletes all records from database and from client by id")
    public void testDeleteAllMethodDeletesAllRecordsFromDatabaseAndFromClientById() {
        EntryIdView entryIdView = () -> 123;
        ArrayList<EntryIdView> list = new ArrayList<>();
        list.add(entryIdView);
        Mockito.when(productRepository.findAllProjectionBy()).thenReturn(list);

        Mockito.when(productsClient.delete(Mockito.anyString())).thenReturn(new ProductDeleteResponse());

        int count = productsManipulationService.deleteAll();

        ArgumentCaptor<String> args = ArgumentCaptor.forClass(String.class);
        Mockito.verify(productsClient, Mockito.times(1)).delete(args.capture());
        Mockito.verify(productRepository, Mockito.times(1)).deleteAllById(Mockito.any());

        assertEquals("123", args.getValue());
        assertEquals(1, count);
    }

    @Test
    @DisplayName("getDatabaseProductsCount method returns database records count")
    public void testGetDatabaseProductsCountMethodReturnsDatabaseRecordsCount() {
        Mockito.when(productRepository.count()).thenReturn(100L);
        long count = productsManipulationService.getDatabaseProductsCount();
        assertEquals(100, count);
    }

    @Test
    @DisplayName("saveResult method saves result into database")
    public void testSaveResultMethodSavesResultIntoDatabase() {
        BulkUpdateProductRequest request = new BulkUpdateProductRequest();

        ArrayList<Product> list = new ArrayList<>();
        ProductResponse item = new ProductResponse();
        item.setCode("code");
        list.add(item);
        request.requests = list;

        BulkResultData result = new BulkResultData();

        ArrayList<BulkResult> list2 = new ArrayList<>();
        BulkResult bulkResult = new BulkResult();
        bulkResult.setResourceId(10);
        list2.add(bulkResult);
        result.setResults(list2);

        Mockito.when(productRepository.saveAll(Mockito.any())).thenAnswer(i -> i.getArguments()[0]);

        List<ProductResponse> saveResult = productsManipulationService.saveResult(request, result);

        assertNotNull(saveResult);
        assertEquals(1, saveResult.size());

        Mockito.verify(productRepository, Mockito.times(1)).saveAll(Mockito.any());
        assertEquals(10, saveResult.get(0).getId());
    }

    @Test
    @DisplayName("generate method generates records and save result")
    public void testGenerateMethodGeneratesRecordsAndSaveResult() {
        BulkResultData bulk = new BulkResultData();

        ArrayList<BulkResult> list = new ArrayList<>();
        BulkResult bulkResult = new BulkResult();
        bulkResult.setResourceId(123);
        list.add(bulkResult);
        bulk.setResults(list);

        Mockito.when(productsBulkUpdateClient.create(Mockito.any(BulkUpdateProductRequest.class))).thenReturn(bulk);
        BulkUpdateProductRequest bulkUpdateProductRequest = new BulkUpdateProductRequest();

        productsManipulationService.generate(1);

        Mockito.verify(productRepository, Mockito.times(1)).saveAll(Mockito.any());
    }

    @Test
    @DisplayName("delete method delete records from client and from database")
    public void testDeleteMethodDeleteRecordsFromClientAndFromDatabase() {
        ProductResponse idProvider = new ProductResponse();
        ArrayList<ProductResponse> list = new ArrayList<>();
        list.add(idProvider);
        Mockito.when(productsClient.read(Mockito.anyString(), Mockito.anyInt(), Mockito.anyInt(), Mockito.anyString(), Mockito.anyString(), Mockito
                .anyBoolean()))
                .thenReturn(list);

        productsManipulationService.delete(1);

        Mockito.verify(productRepository, Mockito.times(1)).deleteAllById(Mockito.any());
        Mockito.verify(productsClient, Mockito.times(1)).delete(Mockito.any());
    }

    @Test
    @DisplayName("read method read records")
    public void testReadMethodReadRecords() {
        PageImpl<ProductResponse> page = new PageImpl<>(new ArrayList<>());
        Mockito.when(productRepository.findAll(Mockito.any(Pageable.class))).thenReturn(page);
        productsManipulationService.read(100);
        Mockito.verify(productRepository, Mockito.times(1)).findAll(Mockito.any(PageRequest.class));
    }

    @Test
    @DisplayName("asProductResponse method created instance with fields copied and id set")
    public void testAsProductResponseMethodCreatedInstanceWithFieldsCopiedAndIdSet() {
        Product product = new Product();
        product.setCode("code1");
        product.setCode2("code2");
        ProductResponse result = productsManipulationService.asProductResponse(123, product);
        assertEquals(123, result.getId());
        assertEquals("code1", result.getCode());
        assertEquals("code2", result.getCode2());
    }

    @Test
    @DisplayName("asProduct method copy fields into product instance")
    public void testAsProductMethodCopyFieldsIntoProductInstance() {
        ProductResponse product = new ProductResponse();
        product.setCode("code1");
        product.setCode2("code2");
        Product result = productsManipulationService.asProduct(product);
        assertEquals("code1", result.getCode());
        assertEquals("code2", result.getCode2());
    }

}
