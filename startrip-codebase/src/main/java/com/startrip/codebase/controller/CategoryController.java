package com.startrip.codebase.controller;

import com.startrip.codebase.domain.category.Category;
import com.startrip.codebase.dto.category.RequestCategoryDto;
import com.startrip.codebase.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping("/category")
    public ResponseEntity<String> createCategory(@RequestBody RequestCategoryDto dto) {
        try {
            categoryService.createCategory(dto);
        } catch(Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("카테고리 생성", HttpStatus.CREATED);
    }

    @PreAuthorize("permitAll")
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

    @PreAuthorize("permitAll")
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
    @PreAuthorize("permitAll")
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
    @PreAuthorize("permitAll")
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

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PutMapping("/category/{id}")
    public ResponseEntity updateEvent(@PathVariable("id") UUID id, RequestCategoryDto dto) {
        try {
            categoryService.updateCategory(id, dto);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("수정", HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
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