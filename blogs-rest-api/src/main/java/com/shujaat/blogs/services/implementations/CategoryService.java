package com.shujaat.blogs.services.implementations;

import com.shujaat.blogs.entities.Category;
import com.shujaat.blogs.entities.Comment;
import com.shujaat.blogs.exceptions.ResourceNotFoundException;
import com.shujaat.blogs.payloads.CategoryDto;
import com.shujaat.blogs.respositories.CategoryRepository;
import com.shujaat.blogs.services.interfaces.ICategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryService implements ICategoryService {
    private CategoryRepository categoryRepository;
    private ModelMapper modelMapper;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository, ModelMapper modelMapper) {
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public CategoryDto addCategory(CategoryDto categoryDto) {
        Category category = modelMapper.map(categoryDto, Category.class);
        Category createdCategory = categoryRepository.save(category);
        return modelMapper.map(createdCategory, CategoryDto.class);
    }

    @Override
    public CategoryDto getCategory(long id) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category", "id", id));
        return modelMapper.map(category, CategoryDto.class);
    }
}
