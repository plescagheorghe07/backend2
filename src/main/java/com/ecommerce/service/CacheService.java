package com.ecommerce.service;

import com.ecommerce.dto.common.MessageResponse;
import org.springframework.stereotype.Service;

@Service
public class CacheService {

    private final ProductService productService;
    private final CategoryService categoryService;

    public CacheService(ProductService productService, CategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }

    public MessageResponse clearAll() {
        productService.clearProductCache();
        categoryService.clearCategoryCache();
        return new MessageResponse("Cache golit");
    }
}
