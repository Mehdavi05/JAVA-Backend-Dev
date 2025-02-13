package com.shujaat.blogs.controllers;

import com.shujaat.blogs.payloads.CategoryDto;
import com.shujaat.blogs.payloads.CategoryDtoV2;
import com.shujaat.blogs.payloads.CategoryResponse;
import com.shujaat.blogs.services.interfaces.ICategoryService;
import com.shujaat.blogs.utils.AppConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@Tag(
        name = "CRUD REST APIs for Category Resource"
)
public class CategoryController {
    private ICategoryService categoryService;

    @Autowired
    public CategoryController(ICategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Operation(
            summary = "Create Category REST API",
            description = "This API is used to create category in the database"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Http Status 201 CREATED"
    )
    @SecurityRequirement(
            name = "Bearer Token"
    )
    @PostMapping("api/v1/category")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CategoryDto> createCategory(@RequestBody CategoryDto categoryDto){
        CategoryDto savedCategory = categoryService.addCategory(categoryDto);
        return new ResponseEntity<>(savedCategory, HttpStatus.CREATED);
    }

    @Operation(
            summary = "GET Category REST API",
            description = "This API is used to get category from the database with the given id"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 201 OK"
    )

    @GetMapping("api/v1/category/{id}")
    public ResponseEntity<CategoryDto> getCategoryV1(@PathVariable(name = "id") long id){
        CategoryDto fetchedCategory = categoryService.getCategory(id);
        return new ResponseEntity<>(fetchedCategory, HttpStatus.OK);
    }


    @GetMapping("api/v2/category/{id}")
    public ResponseEntity<CategoryDtoV2> getCategoryV2(@PathVariable(name = "id") long id){
        CategoryDto fetchedCategory = categoryService.getCategory(id);

        CategoryDtoV2 categoryDtoV2 = new CategoryDtoV2();
        categoryDtoV2.setId((fetchedCategory.getId()));
        categoryDtoV2.setName(fetchedCategory.getName());
        categoryDtoV2.setDescription(fetchedCategory.getDescription());

        List<String> tags = new ArrayList<>();
        tags.add("Awesome");
        tags.add("Splendid");
        tags.add("Too Good");

        categoryDtoV2.setTags(tags);

        return new ResponseEntity<>(categoryDtoV2, HttpStatus.OK);
    }

    @Operation(
            summary = "GET Categories REST API",
            description = "This API is used to get categories from the database with pagination applied"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 201 OK"
    )
    @GetMapping("api/v1/category")
    public ResponseEntity<CategoryResponse> getCategories(
            @RequestParam(name = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(name = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(name = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(name = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir) {
        CategoryResponse response = categoryService.getAllCategories(pageNo, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(
            summary = "UPDATE Category REST API",
            description = "This API is used to update category in the database with the given id"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 OK"
    )
    @SecurityRequirement(
            name = "Bearer Token"
    )
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("api/v1/category/{id}")
    public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto, @PathVariable long id){
        CategoryDto dto = categoryService.updateCategory(categoryDto, id);
        return  new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @Operation(
            summary = "DELETE Category REST API",
            description = "This API is used to update category in the database with the given id"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 OK"
    )
    @SecurityRequirement(
            name = "Bearer Token"
    )
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/api/v1/category/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable long id){
        categoryService.deleteCategory(id);
        return new ResponseEntity<>("Category with the given id deleted successfully", HttpStatus.OK);
    }
}
