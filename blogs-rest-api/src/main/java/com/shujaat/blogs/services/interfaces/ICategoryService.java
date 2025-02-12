package com.shujaat.blogs.services.interfaces;

import com.shujaat.blogs.payloads.CategoryDto;

public interface ICategoryService {
    CategoryDto addCategory(CategoryDto categoryDto);
    CategoryDto getCategory(long id);
}
