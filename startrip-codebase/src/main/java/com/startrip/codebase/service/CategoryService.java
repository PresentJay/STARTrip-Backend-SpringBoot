package com.startrip.codebase.service;


import com.startrip.codebase.domain.category.Category;
import com.startrip.codebase.domain.category.CategoryRepository;
import com.startrip.codebase.domain.category.dto.CreateCategoryDto;
import com.startrip.codebase.domain.category.dto.UpdateCategoryDto;
import com.startrip.codebase.domain.event.Event;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.apache.bcel.classfile.annotation.RuntimeInvisAnnos;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.aspectj.runtime.internal.Conversions.longValue;

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

        Category category = Category.createCategory(dto);

        // 상위 카테고리 넣어주기
        if(dto.getCategoryParentId() == null){
            /* 대분류의 생성일 경우 */
            // 진짜 Root가 없는지 확인하고.
            Category rootCategory = categoryRepository.findById((long)1)
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
            Long id = dto.getCategoryParentId();
            Category parentCategory = categoryRepository.findById(id)
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
        Category category =  categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("삭제하려는 카테고리가 존재하지 않음"));

        // category를 찾은 후, 이들을 undefined이름의 객체를 부모 id로 갖도록 만들자
        List<Category> children = getChildren(category.getId());

        // 찾았다면
        if (children != null ) {
            // undefinedParent를 붙여줄 건데, 사전에 없었으면 만들어 주자.
            Category undefinedParent = categoryRepository.findCategoryByCategoryName("UndefinedParent")
                    .orElseGet(() -> Category.builder()
                            .depth(0)
                            .categoryName("UndefinedParent")
                            .categoryParent(null)
                            .build()
                    );
            categoryRepository.save(undefinedParent);

            for(Category childCategory : children){
                childCategory.setCategoryParent(undefinedParent);
                categoryRepository.save(childCategory);
            }
            // 하위 카테고리가 없던 거라면
        }
        category.setCategoryParent(null);
        categoryRepository.delete(category);
    }

    @Transactional
    public List<Category> getChildren(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("해당 ID의 카테고리가 없습니다"));

        // 파라미터로 받은 ID 를 부모로 가지는 카테고리를 조회한다.
        //Integer max = categoryRepository.findByDepthMax();

        // 해당 ID 를 부모로 가지는 애들 찾는건
        List<Category> results = new ArrayList<>();
        recursionFindChildren(category, results);

        return results;
    }

    // Get : api/category/{id}
    public List<Category> getDepthPlus1Children (Long id ) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("해당 ID의 카테고리가 없습니다."));

        // 현재 카테고리의 depth를 얻자
        Integer depth = category.getDepth() +1;

        // Depth + 1인 아이를 찾자
        List<Category> depthPlus1Categories = categoryRepository.findAllByDepthAndCategoryParent(depth, category);

        return depthPlus1Categories;
    }

    private void recursionFindChildren(Category parent, List<Category> result) {
        List<Category> childList = categoryRepository.findAllByCategoryParent(parent);
        for (Category child : childList) {
            recursionFindChildren(child, result);
            result.add(child);
        }
    }
}