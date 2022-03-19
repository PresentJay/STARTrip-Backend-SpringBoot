package com.startrip.codebase.controller;

import com.startrip.codebase.domain.category.Category;
import com.startrip.codebase.dto.category.NewCategoryDto;
import com.startrip.codebase.dto.category.UpdateCategoryDto;
import com.startrip.codebase.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api")
public class CategoryController {

    private CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    // 카테고리 생성 API /api/category (POST)  Update, Delete (CRUD)
    @PostMapping("/category")
    public String newCategory(@RequestBody NewCategoryDto dto) {
        categoryService.newCategory(dto);
        return dto.getName() + " 생성됬습니다";
    }

    @GetMapping("/category")
    public List<Category> getCategory() {
        List<Category> categories = categoryService.getCategory();
        return categories;
    }

    @PostMapping("/category/{id}")
    public String updateCategory(@PathVariable("id") Long id, @RequestBody UpdateCategoryDto dto) {
        try {
            categoryService.updateCategory(id, dto);
        } catch (Exception e) {
            return e.getMessage();
        }
        return "수정됬습니다.";
    }

    @DeleteMapping("/category/{id}")
    public String deleteCategory(@PathVariable("id") Long id) {
        categoryService.deleteCategory(id);
        return id + " 삭제됬습니다.";
    }
}
