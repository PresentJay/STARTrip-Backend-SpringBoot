package com.startrip.codebase.service;


import com.startrip.codebase.domain.category.Category;
import com.startrip.codebase.domain.category.CategoryRepository;
import com.startrip.codebase.domain.category.dto.CreateCategoryDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class CategoryService {

    private final CategoryRepository categoryRepository;

    // 인자 하나만 있는 건 스프링 특정 버전 이후부턴 알아서 인식한다고 했었음.
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public void createCategory(CreateCategoryDto dto) {
        Category category = Category.builder()
                .categoryParentID(dto.getCategoryParentId()) //create할 땐 parent를 안 넣나?
                .categoryName(dto.getCategoryName())
                .depth(dto.getDepth())
                .build();
        categoryRepository.save(category);
        log.info(category.toString());
    }

    public List<Category> getCategory() {
        List<Category> categories = categoryRepository.findAll();
        return categories;
    }

}