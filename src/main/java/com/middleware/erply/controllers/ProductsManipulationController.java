package com.middleware.erply.controllers;

import lombok.RequiredArgsConstructor;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.middleware.erply.model.ProductDeleteResponse;
import com.middleware.erply.model.product.ProductResponse;
import com.middleware.erply.services.ProductsManipulationService;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@SecurityRequirement(name = "bearerAuth")
@RequiredArgsConstructor
@RestController
@RequestMapping("/products-manipulations")
@Validated
public class ProductsManipulationController {
    private final ProductsManipulationService productsManipulationService;

    @ApiResponses(
            value = { @ApiResponse(
                    responseCode = "200",
                    description = "Generate one to ten products with random data on Erply server. Store data into local database.")
            })
    @GetMapping("/generate")
    public List<ProductResponse> generate(
            @RequestParam(name = "count") @Valid @Min(1) @Max(10) Integer productsCount) {
        return productsManipulationService.generate(productsCount);
    }

    @ApiResponses(
            value = { @ApiResponse(
                    responseCode = "200",
                    description = "Delete one to ten product records from Erply server and from local database.") })
    @GetMapping("/delete")
    public ProductDeleteResponse delete(
            @RequestParam(name = "count") @Valid @Min(1) @Max(10) Integer productsCount) {
        return productsManipulationService.delete(productsCount);
    }

    @ApiResponses(
            value = { @ApiResponse(responseCode = "200", description = "Read one to ten product records from local database.") })
    @GetMapping("/read")
    public List<ProductResponse> read(
            @RequestParam(name = "count") @Valid @Min(1) @Max(10) Integer productsCount) {
        return productsManipulationService.read(productsCount);
    }

    @ApiResponses(
            value = { @ApiResponse(
                    responseCode = "200",
                    description = "Updates one to ten products text data on Erply server records and local database records.") })
    @GetMapping("/update")
    public List<ProductResponse> updateRandomFieds(
            @RequestParam(name = "count") @Valid @Min(1) @Max(10) Integer productsCount) {
        return productsManipulationService.updateRandomFieds(productsCount);
    }

    @ApiResponses(
            value = { @ApiResponse(
                    responseCode = "200",
                    description = "Delete all records from local database and same records from Erply server.") })
    @GetMapping("/deleteAll")
    public int deleteAll() {
        return productsManipulationService.deleteAll();
    }

    @ApiResponses(
            value = { @ApiResponse(
                    responseCode = "200",
                    description = "Retireive local database records count.") })
    @GetMapping("/count")
    public long getDatabaseProductsCount() {
        return productsManipulationService.getDatabaseProductsCount();
    }
}
