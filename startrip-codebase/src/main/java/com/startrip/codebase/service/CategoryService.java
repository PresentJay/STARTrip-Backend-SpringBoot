package com.startrip.codebase.service;


import com.startrip.codebase.domain.category.Category;
import com.startrip.codebase.domain.category.CategoryRepository;
import com.startrip.codebase.domain.category.dto.CreateCategoryDto;
import com.startrip.codebase.domain.category.dto.UpdateCategoryDto;
import com.startrip.codebase.domain.event.Event;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class CategoryService {

    //예외처리 진행되지 않은 상태
    private final CategoryRepository categoryRepository;

    // 인자 하나만 있는 건 스프링 특정 버전 이후부턴 알아서 인식한다고 했었음.
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    // POST: api/categories
    public void createCategory(CreateCategoryDto dto) {

        //생성
        Category category = Category.createCategory(dto);

        // 상위 카테고리 넣어주기
        if(dto.getCategoryParentId() == null){
            /* 대분류의 생성일 경우 */
            // 진짜 Root가 없는지 확인하고.
            Category rootCategory = categoryRepository.findById(0L)
                .orElseGet( ()-> Category.builder()
                    .depth(0)
                    .categoryName("ROOT")
                    .build()
                );
            categoryRepository.save(rootCategory);

            category.setDepth(1);
            category.setCategoryParent(rootCategory);
        } else {
            /* 하위분류의 생성일 경우 */

            // 해당 부모카테고리가 있는지 찾아본다, Optional이므로 없으면 예외발생
            Long categoryParentId = dto.getCategoryParentId();
            Category parentCategory = categoryRepository.findById(categoryParentId)
                    .orElseThrow(() -> new IllegalArgumentException("부모 카테고리 부재"));

            category.setDepth(parentCategory.getDepth() +1);
            category.setCategoryParent(parentCategory);
        }

        categoryRepository.save(category); //저장
        log.info(category.getCategoryName());
    }

    // GET: api/categories,
    public List<Category> getCategoryList() {
        List<Category> categories = categoryRepository.findAll();
        return categories;
    }


    // GET: api/categories/{id}
    public Category getCategory(Long id){
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 카테고리가 존재하지 않음"));

        return category;
    }

    // Patch: api/categories/{id}
    public void updateCategory(Long id, UpdateCategoryDto dto){
        // 조회
        Category category = categoryRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("수정하려는 카테고리가 존재하지 않음"));
        // 상위 카테고리를 변경할 수 있도록 해야하는가?
        //우선, 이름만 변경할 수 있도록
        category.setCategoryName(dto.getCategoryName());
    }

    // Delete: api/categories/{id}
    @Transactional
    public void deleteCategory(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("삭제하려는 카테고리가 존재하지 않음"));

        categoryRepository.deleteById(id);
    }
}