package com.ecommerce.controller;

import com.ecommerce.dto.common.MessageResponse;
import com.ecommerce.service.CacheService;
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

    private final CacheService cacheService;

    public CacheController(CacheService cacheService) {
        this.cacheService = cacheService;
    }

    @DeleteMapping
    @Operation(summary = "Goleste cache-ul de produse si categorii")
    @ApiResponse(responseCode = "200", description = "Cache golit")
    public MessageResponse clearAll() {
        return cacheService.clearAll();
    }
}
