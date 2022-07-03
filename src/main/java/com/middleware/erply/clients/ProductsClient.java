package com.middleware.erply.clients;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.middleware.erply.configurations.FeignAuthHeadersConfig;
import com.middleware.erply.model.ProductDeleteResponse;
import com.middleware.erply.model.product.Product;
import com.middleware.erply.model.product.ProductResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(
        url = "${productsUrl}",
        path = "/v1/product",
        name = "products",
        configuration = {FeignAuthHeadersConfig.class})
public interface ProductsClient {

    @PostMapping
    String create(
            @RequestBody Product product);

    /**
     * Get Products, Product pictures: CDN API
     *
     * Get Products, NB: Filtering & sorting is not supported for the following fields: length, delivery_time, packaging_type, alcohol_registry_number, alcohol_percentage
     * batches, excise_declaration_number, tax_free, is_regular_gift_card, reward_points_not_allowed, non_stock_product, cashier_must_enter_price, labels_not_needed, deposit_fee_amount
     */
    @GetMapping
    List<ProductResponse> read(
            @RequestParam(name = "fields") String fields,
            @RequestParam(name = "skip") int skip,
            @RequestParam(name = "take") int take,
            @RequestParam(name = "sort") String sort,
            @RequestParam(name = "filter") String filter,
            @RequestParam(name = "withTotalCount") Boolean withTotalCount);


    /**
     * @param ids
     *            string
     *            (path)
     *            up to 100 semicolon delimited product IDs
     *            But for some reason does not work for comma separated values, so it is not bulk but single instance delete.
     */
    @DeleteMapping("/{ids}")
    ProductDeleteResponse delete(
            @PathVariable(name = "ids") String ids);
}
