package com.startrip.codebase.service;

import com.startrip.codebase.domain.category.Category;
import com.startrip.codebase.domain.category.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class CategoryServiceTest {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CategoryRepository categoryRepository;

    void setup1() {
        Category root = Category.builder()
                .categoryName("ROOT")
                .categoryParent(null)
                .depth(0)
                .build();
        categoryRepository.save(root);

        Category category1 = Category.builder()
                .categoryName("맛집")
                .categoryParent(root)
                .depth(1)
                .build();
        categoryRepository.save(category1);

        Category category2 = Category.builder()
                .categoryName("식당")
                .categoryParent(category1)
                .depth(2)
                .build();
        categoryRepository.save(category2);

        Category category3 = Category.builder()
                .categoryName("초밥집")
                .categoryParent(category2)
                .depth(3)
                .build();
        categoryRepository.save(category3);

        Category category4 = Category.builder()
                .categoryName("경관")
                .categoryParent(root)
                .depth(1)
                .build();
        categoryRepository.save(category4);

        Category category5 = Category.builder()
                .categoryName("자연")
                .categoryParent(category4)
                .depth(2)
                .build();
        categoryRepository.save(category5);

        Category category6 = Category.builder()
                .categoryName("인조물")
                .categoryParent(category4)
                .depth(2)
                .build();
        categoryRepository.save(category6);
    }

    @DisplayName("경관 카테고리의 자식들을 조회한다")
    @Test
    void get_childrens () {
        setup1();

        List<Category> childrens = categoryService.getChildrens("경관");

        assertThat(childrens.get(0).getCategoryName()).isEqualTo("자연");
        assertThat(childrens.get(1).getCategoryName()).isEqualTo("인조물");
    }


    @DisplayName("ROOT 카테고리의 자식들을 조회한다")
    @Test
    void get_childrens_2 () {
        setup1();

        List<Category> childrens = categoryService.getChildrens("ROOT");

        assertThat(childrens).hasSize(6);
    }

    @DisplayName("경관 카테고리를 삭제환다")
    @Test
    void 경관_삭제 () {
        setup1();

        categoryService.deleteCategory("경관");
        List<Category> childrens = categoryService.getChildrens("UndefinedParent");

        assertThat(childrens.get(0).getCategoryParent().getCategoryName()).isEqualTo("UndefinedParent");
        assertThat(childrens.get(1).getCategoryParent().getCategoryName()).isEqualTo("UndefinedParent");
    }
}