package com.shujaat.blogs.controllers;

import com.shujaat.blogs.payloads.CategoryDto;
import com.shujaat.blogs.services.implementations.CategoryService;
import com.shujaat.blogs.services.interfaces.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/category")
public class CategoryController {
    private ICategoryService categoryService;

    @Autowired
    public CategoryController(ICategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CategoryDto> createCategory(@RequestBody CategoryDto categoryDto){
        CategoryDto savedCategory = categoryService.addCategory(categoryDto);
        return new ResponseEntity<>(savedCategory, HttpStatus.CREATED);
    }
}
