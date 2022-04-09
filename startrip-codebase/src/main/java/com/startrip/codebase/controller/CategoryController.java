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
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping("/category")
    public ResponseEntity<String> createCategory(@RequestBody CreateCategoryDto dto) {
        try {
            categoryService.createCategory(dto);
        } catch(Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("카테고리 생성", HttpStatus.CREATED);
    }

    @GetMapping("/category")
    public ResponseEntity<List<Category>> getCategory() {
        List<Category> categories;
        try {
            categories = categoryService.getCategoryList();
        }catch(Exception e){
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @GetMapping("/category/{id}")
    public ResponseEntity getCategory(@PathVariable("id") UUID id) {
        Category category;
        try {
            category = categoryService.getCategory(id);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(category, HttpStatus.OK);
    }

    // GET All child
    @GetMapping("/category/child-full/{id}")
    public ResponseEntity getCategoryChildren(@PathVariable("id") UUID id) {
        List<Category> children;
        try {
            children = categoryService.getChildrenCategory(id);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(children, HttpStatus.OK);
    }

    // GET depth+1 child
    @GetMapping("category/child/{id}")
    public ResponseEntity getCategoryChildren1Depth(@PathVariable("id") UUID id) {
        List<Category> children;
        try {
            children = categoryService.getDepthPlus1ChildrenCategory(id);
            } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(children, HttpStatus.OK);
    }

    @PutMapping("/category/{id}")
    public ResponseEntity updateEvent(@PathVariable("id") UUID id, @RequestBody UpdateCategoryDto dto) {
        try {
            categoryService.updateCategory(id, dto);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("수정", HttpStatus.OK);
    }

    @DeleteMapping("/category/{id}")
    public ResponseEntity deleteCategory(@PathVariable("id") @NotBlank UUID id) {
        try {
            categoryService.deleteCategory(id);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("삭제", HttpStatus.OK);
    }
}