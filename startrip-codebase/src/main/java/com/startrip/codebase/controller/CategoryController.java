package com.startrip.codebase.controller;

import com.startrip.codebase.domain.category.Category;
import com.startrip.codebase.domain.category.CategoryRepository;
import com.startrip.codebase.domain.category.dto.CreateCategoryDto;
import com.startrip.codebase.domain.category.dto.UpdateCategoryDto;
import com.startrip.codebase.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping("/categories")
    public ResponseEntity<String> createCategory(@RequestBody CreateCategoryDto dto) {
        categoryService.createCategory(dto);
        return new ResponseEntity<>("카테고리 생성", HttpStatus.OK);
    }


    @GetMapping("/categories")
    public ResponseEntity<List<Category>> getCategory() {
        List<Category> categories = categoryService.getCategoryList();
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    // 단일 보기
    @GetMapping("/categories/{id}")
    public ResponseEntity getCategory(@PathVariable("id") Long id) {
        Category category;
        try{
            category = categoryService.getCategory(id);
        } catch (IllegalArgumentException e){
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(category, HttpStatus.OK);
    }

    // 수정
    @PatchMapping("/categories/{id}")
    public ResponseEntity<String> updateEvent(@PathVariable("id") Long id, @RequestBody UpdateCategoryDto dto) {
        try{
            categoryService.updateCategory(id, dto);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("수정", HttpStatus.OK);
    }

    // 삭제
    @DeleteMapping("/categories/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable("id") Long id){
        categoryService.deleteCategory(id);
        return new ResponseEntity<>("삭제", HttpStatus.OK);
    }

}
