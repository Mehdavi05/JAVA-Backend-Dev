package com.shujaat.blogs.services.implementations;

import com.shujaat.blogs.entities.Category;
import com.shujaat.blogs.entities.Comment;
import com.shujaat.blogs.entities.Post;
import com.shujaat.blogs.exceptions.ResourceNotFoundException;
import com.shujaat.blogs.payloads.CategoryDto;
import com.shujaat.blogs.payloads.CategoryResponse;
import com.shujaat.blogs.payloads.PostDto;
import com.shujaat.blogs.payloads.PostResponse;
import com.shujaat.blogs.respositories.CategoryRepository;
import com.shujaat.blogs.services.interfaces.ICategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

    @Override
    public CategoryResponse getAllCategories(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<Category> categories = categoryRepository.findAll(pageable);

        //Get content for Page object
        List<Category> postsList = categories.getContent();

        List<CategoryDto> content = postsList.stream().map(category -> modelMapper.map(category, CategoryDto.class)).collect(Collectors.toList());

        CategoryResponse categoryResponse = new CategoryResponse();
        categoryResponse.setContent(content);
        categoryResponse.setPageNo(categories.getNumber());
        categoryResponse.setPageSize(categories.getSize());
        categoryResponse.setTotalElements(categories.getTotalElements());
        categoryResponse.setTotalPages(categories.getTotalPages());
        categoryResponse.setLast(categories.isLast());

        return categoryResponse;

    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, long id) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category", "id", id));

        category.setName(categoryDto.getName());
        category.setDescription(categoryDto.getDescription());

        Category updatedCategory = categoryRepository.save(category);

        return modelMapper.map(updatedCategory, CategoryDto.class);
    }


}
