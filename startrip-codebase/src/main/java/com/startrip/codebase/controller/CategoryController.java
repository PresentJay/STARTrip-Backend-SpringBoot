package com.startrip.codebase.controller;

import com.startrip.codebase.domain.category.Category;
import com.startrip.codebase.dto.category.CreateCategoryDto;
import com.startrip.codebase.dto.category.UpdateCategoryDto;
import com.startrip.codebase.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.util.List;

@RestController
@RequestMapping("/api")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    // CREATE
    @PostMapping("/category")
    public ResponseEntity<String> createCategory(@RequestBody CreateCategoryDto dto) {
        categoryService.createCategory(dto);
        return new ResponseEntity<>("카테고리 생성", HttpStatus.OK);
    }


    // GET All
    @GetMapping("/category")
    public ResponseEntity<List<Category>> getCategory() {
        List<Category> categories = categoryService.getCategoryList();
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    // GET Only
    @GetMapping("/category/{id}")
    public ResponseEntity getCategory(@PathVariable("id") Long id) {
        Category category;
        try {
            category = categoryService.getCategory(id);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(category, HttpStatus.OK);
    }

    // UPDATE
    @PutMapping("/category/{id}")
    public ResponseEntity updateEvent(@PathVariable("id") Long id, @RequestBody UpdateCategoryDto dto) {
        try {
            categoryService.updateCategory(id, dto);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("수정", HttpStatus.OK);
    }

    // DELETE
    @DeleteMapping("/category/{id}")
    public ResponseEntity deleteCategory(@PathVariable("id") @NotBlank Long id) {
        try {
            categoryService.deleteCategory(id);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("삭제", HttpStatus.OK);
    }


    // GET depth+1 child
    @GetMapping("category/child/{id}")
    public ResponseEntity getCategoryChildren1Depth(@PathVariable("id") Long id) {

        List<Category> children = null;
        try {
            children = categoryService.getDepthPlus1Children(id);
            } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(children, HttpStatus.OK);
    }


    // GET All child
    @GetMapping("/category/child-full/{id}")
    public ResponseEntity getCategoryChildren(@PathVariable("id") Long id) {
        List<Category> children = null;
        try {
            children = categoryService.getChildren(id);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(children, HttpStatus.OK);
    }
}