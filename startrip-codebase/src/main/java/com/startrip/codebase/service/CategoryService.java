package com.startrip.codebase.service;

import com.startrip.codebase.domain.category.Category;
import com.startrip.codebase.domain.category.CategoryRepository;
import com.startrip.codebase.dto.category.RequestCategoryDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public void createCategory(RequestCategoryDto dto) {
        Category category;
        Optional<UUID> parentId = Optional.ofNullable(dto.getCategoryParentId());

        if(parentId.isEmpty()){
            // 대분류의 생성일 경우
            Category rootCategory = categoryRepository.findCategoryByCategoryName("ROOT")
                    .orElseGet( ()-> Category.builder()
                            .depth(0)
                            .categoryName("ROOT")
                            .build()
                    );

            categoryRepository.save(rootCategory);
            category = Category.builder()
                    .depth(1)
                    .categoryName(dto.getCategoryName())
                    .categoryParent(rootCategory)
                    .build();
        } else {
            // 하위분류의 생성일 경우
            Category parentCategory = categoryRepository.findById(parentId.get())
                    .orElseThrow(() -> new RuntimeException("해당하는 부모카테고리가 존재하지 않습니다"));
            category = Category.builder()
                    .depth(parentCategory.getDepth() +1)
                    .categoryName(dto.getCategoryName())
                    .categoryParent(parentCategory)
                    .build();
        }
        categoryRepository.save(category);
    }

    public List<Category> getCategoryList() {
        return categoryRepository.findAll();
    }


    public Category getCategory(UUID id){
        return categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("해당 카테고리가 존재하지 않습니다"));
    }

    @Transactional
    public List<Category> getChildrenCategory(UUID id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("해당 ID의 카테고리가 없습니다"));

        List<Category> results = new ArrayList<>();
        recursionFindChildren(category, results);

        return results;
    }

    public List<Category> getDepthPlus1ChildrenCategory (UUID id ) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("해당 카테고리가 없습니다."));

        Integer depth = category.getDepth() +1;

        return categoryRepository.findAllByDepthAndCategoryParent(depth, category);
    }

    public void updateCategory(UUID id, RequestCategoryDto dto){
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("해당 카테고리가 존재하지 않습니다"));
        category.setCategoryName(dto.getCategoryName());
    }

    @Transactional
    public void deleteCategory(UUID id) {
        Category category =  categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 카테고리가 존재하지 않습니다"));

        List<Category> children = getChildrenCategory(category.getId());

        if (!children.isEmpty() ) {
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
        }
        category.setCategoryParent(null);
        categoryRepository.delete(category);
    }


    private void recursionFindChildren(Category parent, List<Category> result) {
        List<Category> childList = categoryRepository.findAllByCategoryParent(parent);
        for (Category child : childList) {
            recursionFindChildren(child, result);
            result.add(child);
        }
    }
}