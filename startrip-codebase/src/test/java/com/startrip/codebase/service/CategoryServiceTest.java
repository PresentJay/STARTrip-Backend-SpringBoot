package com.startrip.codebase.service;

import com.startrip.codebase.domain.category.Category;
import com.startrip.codebase.domain.category.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigInteger;
import java.util.List;

import static org.aspectj.runtime.internal.Conversions.longValue;
import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
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

        // left-branch
        Category category2 = Category.builder()
                .categoryName("맛집")
                .categoryParent(root)
                .depth(1)
                .build();
        categoryRepository.save(category2);

        Category category3 = Category.builder()
                .categoryName("한식")
                .categoryParent(category2)
                .depth(2)
                .build();
        categoryRepository.save(category3);

        Category category4 = Category.builder()
                .categoryName("중식")
                .categoryParent(category2)
                .depth(2)
                .build();
        categoryRepository.save(category4);

        Category category5 = Category.builder()
                .categoryName("일식")
                .categoryParent(category2)
                .depth(2)
                .build();
        categoryRepository.save(category5);

        Category category6 = Category.builder()
                .categoryName("오마카세")
                .categoryParent(category5)
                .depth(3)
                .build();
        categoryRepository.save(category6);

        // right-branch
        Category category7 = Category.builder()
                .categoryName("관광")
                .categoryParent(root)
                .depth(1)
                .build();
        categoryRepository.save(category7);

        Category category8 = Category.builder()
                .categoryName("자연")
                .categoryParent(category7)
                .depth(2)
                .build();
        categoryRepository.save(category8);

        Category category9 = Category.builder()
                .categoryName("강")
                .categoryParent(category8)
                .depth(3)
                .build();
        categoryRepository.save(category9);

        Category category10 = Category.builder()
                .categoryName("바다")
                .categoryParent(category8)
                .depth(3)
                .build();
        categoryRepository.save(category10);

        Category category11 = Category.builder()
                .categoryName("산")
                .categoryParent(category8)
                .depth(3)
                .build();
        categoryRepository.save(category11);

        Category category12 = Category.builder()
                .categoryName("인조")
                .categoryParent(category7)
                .depth(2)
                .build();
        categoryRepository.save(category12);

        Category category13 = Category.builder()
                .categoryName("랜드마크")
                .categoryParent(category12)
                .depth(3)
                .build();
        categoryRepository.save(category13);

        Category category14 = Category.builder()
                .categoryName("UndefinedParent")
                .categoryParent(root)
                .depth(1)
                .build();
        categoryRepository.save(category14);
    }

    // 얘네 왜 한꺼번에 실행하면 안되고 단일로 실행하면 통과하는 거지?
    @DisplayName("step1: 맛집 카테고리의 자식들을 조회")
    @Test
    void test1 () {
        setup1();
        List<Category> children = categoryService.getChildren((long)2);

        assertThat(children.get(0).getCategoryName()).isEqualTo("한식");
        assertThat(children.get(1).getCategoryName()).isEqualTo("중식");
    }

    @DisplayName("Step2: Category-Service GET-children Test")
    @Test
    void test2 () {
        List<Category> children = categoryService.getChildren((long)1);

        assertThat(children).hasSize(13);
    }

    @DisplayName("Step3: Category-Service GET-children(Depth+1) Test")
    @Test
    void test3 () {
        List<Category> children = categoryService.getDepthPlus1Children((long)1);

        assertThat(children).hasSize(3);   // 맛집, 관광, undefinedParent
    }

    @DisplayName("Step4: Category-Service GET-removedItem Test")
    @Test
    void test4 () {
        categoryService.deleteCategory((long)2 );
        List<Category> children = categoryService.getChildren((long)14);

        assertThat(children.get(0).getCategoryName()).isEqualTo("한식");
        assertThat(children.get(1).getCategoryName()).isEqualTo("중식");
    }
}
