package com.middleware.erply.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.middleware.erply.configurations.FeignAuthHeadersConfig;
import com.middleware.erply.model.product.bulk.BulkResultData;
import com.middleware.erply.model.product.bulk.BulkUpdateProductRequest;

@FeignClient(
        url = "${productsUrl}",
        path = "/v1/product/bulk",
        name = "productsBulkUpdate",
        configuration = { FeignAuthHeadersConfig.class })
public interface ProductsBulkUpdateClient {

    @PostMapping
    BulkResultData create(
            @RequestBody BulkUpdateProductRequest request);

    @PutMapping
    BulkResultData update(
            @RequestBody BulkUpdateProductRequest request);

}
