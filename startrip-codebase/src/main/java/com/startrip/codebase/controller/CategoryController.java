package com.startrip.codebase.controller;

import com.startrip.codebase.domain.category.Category;
import com.startrip.codebase.domain.category.dto.CreateCategoryDto;
import com.startrip.codebase.domain.event.Event;
import com.startrip.codebase.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/category")
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }


    //생성
    @PostMapping("/create")
    public String createCategory(@RequestBody CreateCategoryDto dto) {
        categoryService.createCategory(dto);
        return dto.getCategoryName() + " 생성";
    }

    //목록 보기
    @GetMapping("/list")
    public List<Category> getCategory() {
        List<Category> categories = categoryService.getCategoryList();
        return categories;
    }

    // 단일 보기
    @GetMapping("/get/{id}")
    public Category getCategory(@PathVariable("id") Long id) {
        Category category = categoryService.getCategory(id);
        return category;
    }



}
