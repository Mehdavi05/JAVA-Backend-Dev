package com.shujaat.blogs.services.interfaces;

import com.shujaat.blogs.payloads.CategoryDto;
import com.shujaat.blogs.payloads.CategoryResponse;
import com.shujaat.blogs.payloads.PostDto;

import java.util.List;

public interface ICategoryService {
    CategoryDto addCategory(CategoryDto categoryDto);
    CategoryDto getCategory(long id);
    CategoryResponse getAllCategories(int pageNo, int pageSize, String sortBy, String sortDir);
    CategoryDto updateCategory(CategoryDto categoryDto, long id);
    void deleteCategory(long id);

}
