package com.startrip.codebase.service;


import com.startrip.codebase.domain.category.Category;
import com.startrip.codebase.domain.category.CategoryRepository;
import com.startrip.codebase.domain.category.dto.CreateCategoryDto;
import com.startrip.codebase.domain.event.Event;
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

    public List<Category> getCategoryList() {
        List<Category> categories = categoryRepository.findAll();
        return categories;
    }

    public Category getCategory(Long id){
        //왜 나는 id를 integer로 받아야한다고 그럴까? id를 Integer로 설정한 곳이 없는 것 같은데.
        return categoryRepository.findById(id).get();
    }

    public void deleteCategory(Long id){
        categoryRepository.deleteById(id);
    }



}