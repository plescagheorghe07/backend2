package com.ecommerce.controller;

import com.ecommerce.dto.common.MessageResponse;
import com.ecommerce.service.CategoryService;
import com.ecommerce.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cache")
@Tag(name = "Cache", description = "Golirea cache-ului Redis")
public class CacheController {

    private final ProductService productService;
    private final CategoryService categoryService;

    public CacheController(ProductService productService, CategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }

    @DeleteMapping
    @Operation(summary = "Goleste cache-ul de produse si categorii")
    @ApiResponse(responseCode = "200", description = "Cache golit")
    public MessageResponse clearAll() {
        productService.clearProductCache();
        categoryService.clearCategoryCache();
        return new MessageResponse("Cache golit");
    }
}
