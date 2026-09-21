package com.ecommerce.service;

import com.ecommerce.domain.Product;
import com.ecommerce.dto.product.ProductResponse;
import com.ecommerce.repository.ProductRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Cacheable(cacheNames = "products", key = "#categoryId != null ? #categoryId : 'all'")
    @Transactional(readOnly = true)
    public List<ProductResponse> list(Long categoryId) {
        List<Product> products = categoryId == null
                ? productRepository.findAll()
                : productRepository.findByCategoryId(categoryId);
        return products.stream()
                .map(this::toResponse)
                .toList();
    }

    @CacheEvict(cacheNames = "products", allEntries = true)
    public void clearProductCache() {
    }

    private ProductResponse toResponse(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getStock(),
                product.getCategory().getId(),
                product.getCategory().getName()
        );
    }
}
