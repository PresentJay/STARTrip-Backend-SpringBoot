package com.startrip.codebase.service;

import com.startrip.codebase.domain.category.Category;
import com.startrip.codebase.domain.category.CategoryRepository;
import com.startrip.codebase.dto.category.NewCategoryDto;
import com.startrip.codebase.dto.category.UpdateCategoryDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    private CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public void newCategory(NewCategoryDto dto) {
        Category category = Category.builder()
                .depth(dto.getDepth())
                .name(dto.getName())
                .build();
        categoryRepository.save(category);
    }

    public List<Category> getCategory() {
        List<Category> categories = categoryRepository.findAll();
        return categories;
    }

    public void updateCategory(Long id, UpdateCategoryDto dto) {
        Optional<Category> category = categoryRepository.findById(id);
        if (category.isEmpty()) {
            throw new RuntimeException("존재하지 않는 카테고리입니다.");
        }
        Category find = category.get();
        find.update(dto);

        categoryRepository.save(find);
    }

    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }
}
