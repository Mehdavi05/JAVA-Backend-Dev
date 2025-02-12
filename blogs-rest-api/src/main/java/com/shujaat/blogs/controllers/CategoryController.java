package com.shujaat.blogs.controllers;

import com.shujaat.blogs.payloads.CategoryDto;
import com.shujaat.blogs.payloads.CategoryResponse;
import com.shujaat.blogs.payloads.PostDto;
import com.shujaat.blogs.services.implementations.CategoryService;
import com.shujaat.blogs.services.interfaces.ICategoryService;
import com.shujaat.blogs.utils.AppConstants;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDto> getCategory(@PathVariable(name = "id") long id){
        CategoryDto fetchedCategory = categoryService.getCategory(id);
        return new ResponseEntity<>(fetchedCategory, HttpStatus.OK);
    }
    @GetMapping
    public ResponseEntity<CategoryResponse> getCategories(
            @RequestParam(name = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(name = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(name = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(name = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir) {
        CategoryResponse response = categoryService.getAllCategories(pageNo, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto, @PathVariable long id){
        CategoryDto dto = categoryService.updateCategory(categoryDto, id);
        return  new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable long id){
        categoryService.deleteCategory(id);
        return new ResponseEntity<>("Category with the given id deleted successfully", HttpStatus.OK);
    }
}
