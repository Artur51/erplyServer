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
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductsManipulationService {
    private static final String fields = String.join(",", Product.fieldNames);

    private final ProductsBulkUpdateClient productsBulkUpdateClient;
    private final ProductsClient productsClient;
    private final ProductRepository productRepository;
    private final ProductInstanceGenerationService productInstanceGenerationService;

    public List<ProductResponse> generate(
            int productsCount) {
        BulkUpdateProductRequest request = productInstanceGenerationService.generateRequest(Product::new, productsCount);
        BulkResultData result = productsBulkUpdateClient.create(request);
        return saveResult(request, result);
    }

    public ProductDeleteResponse delete(
            int productsCount) {
        List<ProductResponse> ids = productsClient.read("" + ProductResponse.Fields.id, 0, productsCount, null, null, false);
        String idsString = ids.stream().map(ProductResponse::getId).map(a -> a.toString()).collect(Collectors.joining(";"));
        List<Integer> idsList = ids.stream().map(ProductResponse::getId).collect(Collectors.toList());
        productRepository.deleteAllById(idsList);
        return productsClient.delete(idsString);
    }

    public List<ProductResponse> read(
            int productsCount) {
        return productRepository.findAll(PageRequest.of(0, productsCount)).getContent();
    }

    public List<ProductResponse> updateRandomFieds(
            int productsCount) {
        List<ProductResponse> content = read(productsCount);

        productInstanceGenerationService.updateStringFields(content);
        List<Product> products = content.stream().map(this::asProduct).collect(Collectors.toList());

        BulkUpdateProductRequest request = new BulkUpdateProductRequest(products);
        productsBulkUpdateClient.update(request);

        return productRepository.saveAll(content);
    }

    public int deleteAll() {
        List<EntryIdView> ids = productRepository.findAllProjectionBy();
        final int count = ids.size();
        int batchCount = 100;
        while (!ids.isEmpty()) {
            List<Integer> items = ids.stream().limit(batchCount).map(EntryIdView::getId).collect(Collectors.toList());
            String idsString = items.stream().map(a -> a.toString()).collect(Collectors.joining(";"));

            ProductDeleteResponse result = productsClient.delete(idsString);
            if (result.getMessage() == null) {
                productRepository.deleteAllById(items);
            }
            List<EntryIdView> removed = ids.stream().limit(batchCount).collect(Collectors.toList());
            ids.removeAll(removed);
        }
        return count;
    }

    public long getDatabaseProductsCount() {
        return productRepository.count();
    }

    List<ProductResponse> saveResult(
            BulkUpdateProductRequest request,
            BulkResultData result) {
        ArrayList<ProductResponse> list = new ArrayList<>();

        int id = 0;
        for (var v : request.getRequests()) {
            BulkResult createInfo = result.getResults().get(id);
            int resultId = createInfo.getResourceId();
            ProductResponse response = asProductResponse(resultId, v);
            list.add(response);
            id++;
        }
        return productRepository.saveAll(list);
    }

    ProductResponse asProductResponse(
            int id,
            Product product) {
        ProductResponse result = new ProductResponse();
        BeanUtils.copyProperties(product, result);
        result.setId(id);
        return result;
    }

    Product asProduct(
            ProductResponse v) {
        Product result = new Product();
        BeanUtils.copyProperties(v, result);
        return result;
    }
}
