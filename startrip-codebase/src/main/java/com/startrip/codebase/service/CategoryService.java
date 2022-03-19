package com.startrip.codebase.service;


import com.startrip.codebase.domain.category.CategoryRepository;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    // 인자 하나만 있는 건 스프링 특정 버전 이후부턴 알아서 인식한다고 했었음.
    public CategoryService(CategoryRepository categoryRepository){
        this.categoryRepository = categoryRepository;
    }

    public Long create(Category category) {
        categorys
    }

}
