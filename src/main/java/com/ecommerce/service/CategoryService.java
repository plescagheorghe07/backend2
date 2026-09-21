package com.ecommerce.service;

import com.ecommerce.domain.Category;
import com.ecommerce.dto.category.CategoryResponse;
import com.ecommerce.repository.CategoryRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Cacheable(cacheNames = "categories", key = "'all'")
    @Transactional(readOnly = true)
    public List<CategoryResponse> list() {
        return categoryRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    @CacheEvict(cacheNames = "categories", allEntries = true)
    public void clearCategoryCache() {
    }

    private CategoryResponse toResponse(Category category) {
        return new CategoryResponse(category.getId(), category.getName());
    }
}
