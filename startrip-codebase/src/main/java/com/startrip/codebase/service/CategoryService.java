package com.startrip.codebase.service;


import com.startrip.codebase.domain.category.Category;
import com.startrip.codebase.domain.category.CategoryRepository;
import com.startrip.codebase.domain.category.dto.CreateCategoryDto;
import com.startrip.codebase.domain.category.dto.UpdateCategoryDto;
import com.startrip.codebase.domain.event.Event;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class CategoryService {

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
            category.builder()
                    .categoryParent(rootCategory)
                    .depth(1)
                    .build();
        } else {
            /* 하위분류의 생성일 경우 */

            // 해당 부모카테고리가 있는지 찾아본다, Optional이므로 없으면 예외발생
            Long categoryParentId = dto.getCategoryParentId();
            Category parentCategory = categoryRepository.findById(categoryParentId)
                    .orElseThrow(() -> new IllegalArgumentException("부모 카테고리 부재"));
            category.builder()
                    .categoryParent(parentCategory)
                    .depth(parentCategory.getDepth() + 1)
                    .build();
        }

        categoryRepository.save(category); //저장
        log.info(category.getCategoryName());
    }

    /*
    public List<Category> getCategoryList() {
        List<Category> categories = categoryRepository.findAll();
        return categories;
    }

    public Category getCategory(Long id){
        //왜 나는 id를 integer로 받아야한다고 그럴까? id를 Integer로 설정한 곳이 없는 것 같은데.
        return categoryRepository.findById(id).get();
    }

    public void updateCategory(Long id, UpdateCategoryDto dto){
        // 조회
        Optional<Category> category = categoryRepository.findById(id);
        if (category.isEmpty()){
            throw new RuntimeException("존재하지 않는 카테고리 조회");
        }
        Category use = category.get();

        use.update(dto);

        categoryRepository.save(use);
    }

    public void deleteCategory(Long id){
        categoryRepository.deleteById(id); */
    }



}