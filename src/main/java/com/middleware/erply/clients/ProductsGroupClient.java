package com.middleware.erply.clients;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.middleware.erply.configurations.FeignAuthHeadersConfig;
import com.middleware.erply.model.product.ProductGroup;
import com.middleware.erply.model.product.ProductGroupRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(
        url = "${productsUrl}",
        path = "/v1/product/group",
        name = "productGroups",
        configuration = { FeignAuthHeadersConfig.class })
public interface ProductsGroupClient {

    @PostMapping
    String create(
            @RequestBody ProductGroupRequest group);

    @GetMapping
    List<ProductGroup> read(
            @RequestParam(name = "skip") int skip,
            @RequestParam(name = "take") int take,
            @RequestParam(name = "sort") String sort,
            @RequestParam(name = "filter") String filter,
            @RequestParam(name = "withTotalCount") Boolean withTotalCount);

    @PutMapping
    String update(
            @RequestBody ProductGroupRequest group,
            @PathVariable(name = "id") String id);

    /**
     *
     * @param ids
     *              up to 100 semicolon delimited Group IDs
     */
    @DeleteMapping("/{ids}")
    String delete(
            @PathVariable(name = "ids") String ids);
}
